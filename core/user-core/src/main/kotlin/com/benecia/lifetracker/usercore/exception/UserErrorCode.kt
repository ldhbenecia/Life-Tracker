package com.benecia.lifetracker.usercore.exception

import com.benecia.lifetracker.common.exception.ErrorCode

enum class UserErrorCode(
    override val code: Int,
    override val message: String,
) : ErrorCode {
    USER_NOT_FOUND(404, "해당 ID의 사용자를 찾을 수 없습니다."),

    // ErrorCode 인터페이스의 name 프로퍼티는 enum 클래스에 자동으로 구현됩니다.
}