package com.benecia.lifetracker.auth.exception

import com.benecia.lifetracker.common.exception.ErrorCode

enum class AuthErrorCode(
    override val code: Int,
    override val message: String,
) : ErrorCode {
    OAUTH_USER_INFO_FETCH_FAILED(500, "사용자 정보를 가져오는데 실패하였습니다."),
    OAUTH_TOKEN_EXCHANGE_FAILED(500, "토큰 발급에 실패하였습니다."),
    TOKEN_RESPONSE_MALFORMED(500, "토큰 응답이 올바르지 않습니다."),
    ACCESS_TOKEN_MISSING(500, "토큰 응답에 액세스 토큰이 없습니다."),
    UNSUPPORTED_OAUTH_PROVIDER(400, "지원하지 않는 Auth 공급자입니다."),
}