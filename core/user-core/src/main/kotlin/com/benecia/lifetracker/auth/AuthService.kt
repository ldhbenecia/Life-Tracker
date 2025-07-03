package com.benecia.lifetracker.auth

import com.benecia.lifetracker.auth.exception.AuthErrorCode
import com.benecia.lifetracker.auth.utils.common.OAuthUtils
import com.benecia.lifetracker.auth.utils.google.GoogleOAuthUtils
import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.user.service.User
import com.benecia.lifetracker.user.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val googleOAuthUtils: GoogleOAuthUtils,
    private val userService: UserService
) {

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
    ) : UUID {
        if (error != null) {
            logger.error("OAuth 에러 발생 - provider: $provider, error: $error")
            throw CustomException(AuthErrorCode.OAUTH_USER_INFO_FETCH_FAILED, RuntimeException(error))
        }

        val oAuthUtils = getOAuthUtils(provider)
        val userInfoMap = oAuthUtils.getUserInfoAndTokens(code)

        val user = User(
            provider = provider,
            email = userInfoMap["email"] as? String ?: throw CustomException(AuthErrorCode.OAUTH_USER_INFO_FETCH_FAILED),
            displayName = userInfoMap["name"] as? String ?: "Unknown",
            profileImageUrl = userInfoMap["picture"] as? String,
            accessToken = userInfoMap["accessToken"] as? String,
            refreshToken = userInfoMap["refreshToken"] as? String,
        )

        val userId = userService.add(user)

        logger.info("✅ OAuth 회원가입 완료 - provider: $provider, email: ${user.email}, userId: $userId")

        return userId
    }
}