package com.benecia.lifetracker.auth.utils.common

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class OAuthUserInfoConverter(
    private val restClient: RestClient
) {

    private val logger = LoggerFactory.getLogger(OAuthUserInfoConverter::class.java)

    /**
     * Google 사용자 정보는 그대로 반환 (표준 형식)
     */
    fun convertGoogleUserInfo(
        googleUserInfo: Map<String, Any>,
        tokenInfo: Map<String, Any>
    ): Map<String, Any> {
        return mapOf(
            "id" to (googleUserInfo["id"] as String),
            "name" to (googleUserInfo["name"] as? String ?: ""),
            "given_name" to (googleUserInfo["given_name"] as? String ?: ""),
            "family_name" to (googleUserInfo["family_name"] as? String ?: ""),
            "email" to (googleUserInfo["email"] as String),
            "picture" to (googleUserInfo["picture"] as? String ?: ""),

            "access_token" to (tokenInfo["access_token"] ?: ""),
            "refresh_token" to (tokenInfo["refresh_token"] ?: ""),
            "expires_in" to (tokenInfo["expires_in"] ?: 3600),
            "token_type" to (tokenInfo["token_type"] ?: "Bearer"),
            "scope" to (tokenInfo["scope"] ?: "")
        )
    }

    // naver

    // kakao
}