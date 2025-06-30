package com.benecia.lifetracker.auth.utils.common

interface OAuthUtils {

    /**
     * OAuth 코드로 사용자 정보와 토큰 가져오기
     */
    fun getUserInfoAndTokens(code: String): Map<String, Any>

    /**
     * OAuth 코드로 토큰 가져오기
     */
    fun getTokens(code: String): OAuthTokens

    /**
     * 액세스 토큰으로 사용자 정보 가져오기
     */
    fun getUserInfo(accessToken: String): Map<String, Any>

    /**
     * 리프레시 토큰으로 새 액세스 토큰 발급
     */
    fun refreshAccessToken(refreshToken: String): OAuthTokens

    /**
     * 액세스 토큰 유효성 검사
     */
    fun isTokenValid(accessToken: String): Boolean

    /**
     * 리프레시 토큰 유효성 검사
     */
    fun isRefreshTokenValid(refreshToken: String): Boolean
}