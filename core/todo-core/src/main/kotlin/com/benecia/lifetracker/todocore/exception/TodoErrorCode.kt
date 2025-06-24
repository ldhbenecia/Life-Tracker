package com.benecia.lifetracker.todocore.exception

import com.benecia.lifetracker.common.exception.ErrorCode

enum class TodoErrorCode(
    override val code: Int,
    override val message: String,
) : ErrorCode {
    TODO_NOT_FOUND(404, "해당 ID의 할 일을 찾을 수 없습니다."),
    UNAUTHORIZED_TODO_ACCESS(403, "해당 할 일에 접근할 권한이 없습니다.");

    // ErrorCode 인터페이스의 name 프로퍼티는 enum 클래스에 자동으로 구현됩니다.
}