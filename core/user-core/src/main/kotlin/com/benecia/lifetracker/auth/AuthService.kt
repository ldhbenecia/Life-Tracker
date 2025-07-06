package com.benecia.lifetracker.auth

import com.benecia.lifetracker.auth.exception.AuthErrorCode
import com.benecia.lifetracker.auth.utils.common.OAuthUserInfoConverter
import com.benecia.lifetracker.auth.utils.common.OAuthUtils
import com.benecia.lifetracker.auth.utils.google.GoogleOAuthUtils
import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.security.JwtUtil
import com.benecia.lifetracker.user.service.User
import com.benecia.lifetracker.user.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val googleOAuthUtils: GoogleOAuthUtils,
    private val userService: UserService,
    private val oAuthUserInfoConverter: OAuthUserInfoConverter,
    private val jwtUtil: JwtUtil
) {

    @Value("\${oauth.redirect.web}")
    lateinit var redirectWeb: String

    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    private fun getOAuthUtils(provider: String): OAuthUtils {
        return when (provider.lowercase()) {
            "google" -> googleOAuthUtils
            // naver, kakao 등 확장 가능
            else -> throw CustomException(AuthErrorCode.UNSUPPORTED_OAUTH_PROVIDER)
        }
    }

    fun handleOAuthCallback(
        provider: String,
        code: String,
        error: String?,
        state: String?,
    ): String {
        if (error != null) {
            logger.error("OAuth 에러 발생 - provider: $provider, error: $error")
            throw CustomException(AuthErrorCode.OAUTH_USER_INFO_FETCH_FAILED, RuntimeException(error))
        }

        val oAuthUtils = getOAuthUtils(provider)
        val tokens = oAuthUtils.getTokens(code)
        val rawUserInfo = oAuthUtils.getUserInfo(tokens.accessToken)

        val tokenInfo = mapOf(
            "access_token" to tokens.accessToken,
            "refresh_token" to (tokens.refreshToken ?: ""),
            "expires_in" to (tokens.expiresIn ?: 3600),
            "token_type" to (tokens.tokenType ?: "Bearer"),
        )

        val userInfoMap = when (provider.lowercase()) {
            "google" -> oAuthUserInfoConverter.convertGoogleUserInfo(rawUserInfo, tokenInfo)
            // "naver" -> oAuthUserInfoConverter.convertNaverUserInfo(...)
            // "kakao" -> oAuthUserInfoConverter.convertKakaoUserInfo(...)
            else -> rawUserInfo
        }

        val user = User(
            provider = provider,
            email = userInfoMap["email"] as String,
            displayName = userInfoMap["name"] as? String ?: "Unknown",
            profileImageUrl = userInfoMap["picture"] as? String,
        )

        val userId = userService.add(user)
        val accessToken = jwtUtil.generateToken(userId)
        // val refreshToken = UUID.randomUUID().toString() // → 추후 Redis 저장 예정

        logger.info("✅ OAuth 로그인 완료 - userId: $userId")

        val redirectUrl = when {
            state?.startsWith("myapp://") == true -> "$state?accessToken=$accessToken"
            else -> "$redirectWeb?accessToken=$accessToken"
        }

        return redirectUrl
    }
}