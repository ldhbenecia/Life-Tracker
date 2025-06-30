package com.benecia.lifetracker.auth.utils.common

interface OAuthTokens {
    val accessToken: String
    val refreshToken: String?
    val expiresIn: Long?
    val tokenType: String
}