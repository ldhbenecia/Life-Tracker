package com.benecia.lifetracker.auth.utils.google

import com.benecia.lifetracker.auth.exception.AuthErrorCode
import com.benecia.lifetracker.auth.utils.common.OAuthTokens
import com.benecia.lifetracker.auth.utils.common.OAuthUserInfoConverter
import com.benecia.lifetracker.auth.utils.common.OAuthUtils
import com.benecia.lifetracker.common.exception.CustomException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException

@Component
class GoogleOAuthUtils(
    private val restClient: RestClient,
    private val oAuthUserInfoConverter: OAuthUserInfoConverter,
    @Value("\${google.client-id}") private val clientId: String,
    @Value("\${google.client-secret}") private val clientSecret: String,
    @Value("\${google.redirect-uri}") private val redirectUri: String,
) : OAuthUtils {

    private val logger = LoggerFactory.getLogger(GoogleOAuthUtils::class.java)

    companion object {
        private const val TOKEN_URL = "https://oauth2.googleapis.com/token"
        private const val TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo"
        private const val USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo"
    }

    override fun getUserInfoAndTokens(code: String): Map<String, Any> {
        try {
            val tokens = getTokens(code) as GoogleTokens
            val googleUserInfo = getUserInfo(tokens.accessToken)

            val tokenInfo = mapOf<String, Any>(
                "access_token" to tokens.accessToken,
                "refresh_token" to (tokens.refreshToken ?: ""),
                "expires_in" to (tokens.expiresIn ?: 3600),
                "token_type" to tokens.tokenType,
            )

            val convertedUserInfo = oAuthUserInfoConverter.convertGoogleUserInfo(
                googleUserInfo, tokenInfo
            )

            val email = convertedUserInfo["email"] as String
            logger.info("✅ Google OAuth 사용자 정보 및 토큰 성공적으로 조회 - email: {}", email)

            return convertedUserInfo
        } catch (error: Exception) {
            logger.error("❌ Google OAuth 사용자 정보 및 토큰 조회 실패", error)
            throw when (error) {
                is CustomException -> error
                else -> CustomException(AuthErrorCode.OAUTH_USER_INFO_FETCH_FAILED, error)
            }
        }
    }

    override fun getTokens(code: String): OAuthTokens {
        val requestBody = LinkedMultiValueMap<String, String>().apply {
            add("code", code)
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("redirect_uri", redirectUri)
            add("grant_type", "authorization_code")
        }

        return try {
            val response = makeTokenRequest(requestBody)
            GoogleTokens.fromResponse(response)
        } catch (error: Exception) {
            logger.error("❌ Google OAuth 토큰 발급 실패 - code: $code", error)
            throw when (error) {
                is CustomException -> error
                else -> CustomException(AuthErrorCode.OAUTH_TOKEN_EXCHANGE_FAILED, error)
            }
        }
    }

    override fun getUserInfo(accessToken: String): Map<String, Any> {
        return try {
            restClient.get()
                .uri(USER_INFO_URL)
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .body(object : ParameterizedTypeReference<Map<String, Any>>() {})
                ?: throw CustomException(AuthErrorCode.OAUTH_USER_INFO_FETCH_FAILED)
        } catch (error: Exception) {
            logger.error("❌ Google 사용자 정보 조회 실패", error)
            throw when (error) {
                is CustomException -> error
                else -> CustomException(AuthErrorCode.OAUTH_USER_INFO_FETCH_FAILED, error)
            }
        }
    }

    @Retryable(
        retryFor = [RestClientException::class],
        maxAttempts = 3,
        backoff = org.springframework.retry.annotation.Backoff(delay = 1000)
    )
    override fun refreshAccessToken(refreshToken: String): OAuthTokens {
        val requestBody = LinkedMultiValueMap<String, String>().apply {
            add("refresh_token", refreshToken)
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("grant_type", "refresh_token")
        }

        return try {
            val response = makeTokenRequest(requestBody)
            GoogleTokens.fromRefreshResponse(response, refreshToken)
        } catch (error: Exception) {
            logger.error("❌ Google OAuth 리프레시 토큰 갱신 실패", error)
            throw when (error) {
                is CustomException -> error
                else -> CustomException(AuthErrorCode.OAUTH_TOKEN_EXCHANGE_FAILED, error)
            }
        }
    }

    override fun isTokenValid(accessToken: String): Boolean {
        if (accessToken.isBlank()) return false

        return try {
            restClient.get()
                .uri(TOKEN_INFO_URL)
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .body(object : ParameterizedTypeReference<Map<String, Any>>() {})

            logger.debug("✅ Google 액세스 토큰 유효성 검사 성공")
            true
        } catch (error: Exception) {
            logger.debug("❌ Google 액세스 토큰 유효성 검사 실패")
            false
        }
    }

    override fun isRefreshTokenValid(refreshToken: String): Boolean {
        if (refreshToken.isBlank()) return false

        return try {
            refreshAccessToken(refreshToken)
            logger.debug("✅ Google 리프레시 토큰 유효성 검사 성공")
            true
        } catch (error: Exception) {
            logger.debug("❌ Google 리프레시 토큰 유효성 검사 실패")
            false
        }
    }

    private fun makeTokenRequest(requestBody: LinkedMultiValueMap<String, String>): Map<String, Any> {
        return try {
            val response = restClient.post()
                .uri(TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .exchange { _, clientResponse ->
                    if (clientResponse.statusCode.is4xxClientError || clientResponse.statusCode.is5xxServerError) {
                        val errorBody = clientResponse.bodyTo(String::class.java)
                        logger.error("❌ Google OAuth API 오류 - 상태코드: ${clientResponse.statusCode}, 응답 내용: $errorBody")
                        throw CustomException(AuthErrorCode.OAUTH_TOKEN_EXCHANGE_FAILED)
                    }
                    clientResponse.bodyTo(object : ParameterizedTypeReference<Map<String, Any>>() {})
                }

            when {
                response == null -> {
                    logger.error("❌ Google 토큰 응답이 null입니다")
                    throw CustomException(AuthErrorCode.TOKEN_RESPONSE_MALFORMED)
                }
                !response.containsKey("access_token") -> {
                    logger.error("❌ Google 토큰 응답에 access_token 없음: $response")
                    throw CustomException(AuthErrorCode.ACCESS_TOKEN_MISSING)
                }
                response.containsKey("error") -> {
                    val errorDesc = response["error_description"] as? String ?: response["error"] as? String
                    logger.error("❌ Google OAuth 에러 응답: $errorDesc")
                    throw CustomException(AuthErrorCode.OAUTH_TOKEN_EXCHANGE_FAILED)
                }
                else -> response
            }
        } catch (error: Exception) {
            when (error) {
                is CustomException -> throw error
                else -> {
                    logger.error("❌ Google 토큰 요청 실패", error)
                    throw CustomException(AuthErrorCode.OAUTH_TOKEN_EXCHANGE_FAILED, error)
                }
            }
        }
    }
}