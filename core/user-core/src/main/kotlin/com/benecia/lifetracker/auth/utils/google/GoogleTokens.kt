package com.benecia.lifetracker.auth.utils.google

import com.benecia.lifetracker.auth.utils.common.OAuthTokens

data class GoogleTokens(
    override val accessToken: String,
    override val refreshToken: String?,
    override val expiresIn: Long?,
    override val tokenType: String = "Bearer",
) : OAuthTokens {
    companion object {
        fun fromResponse(response: Map<String, Any>): GoogleTokens {
            return GoogleTokens(
                accessToken = response["access_token"] as String,
                refreshToken = response["refresh_token"] as? String,
                expiresIn = (response["expires_in"] as? Number)?.toLong(),
                tokenType = response["token_type"] as? String ?: "Bearer",
            )
        }

        fun fromRefreshResponse(response: Map<String, Any>, originalRefreshToken: String): GoogleTokens {
            return GoogleTokens(
                accessToken = response["access_token"] as String,
                refreshToken = response["refresh_token"] as? String ?: originalRefreshToken,
                expiresIn = (response["expires_in"] as? Number)?.toLong(),
                tokenType = response["token_type"] as? String ?: "Bearer",
            )
        }
    }
}
