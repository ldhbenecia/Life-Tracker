package com.benecia.lifetracker.auth.utils.common

interface OAuthUtils {
    fun getTokens(code: String): OAuthTokens //  OAuth 코드로 토큰 가져오기
    fun getUserInfo(accessToken: String): Map<String, Any> // 액세스 토큰으로 사용자 정보 가져오기
}