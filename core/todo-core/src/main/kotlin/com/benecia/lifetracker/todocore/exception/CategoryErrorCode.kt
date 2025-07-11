package com.benecia.lifetracker.todocore.exception

import com.benecia.lifetracker.common.exception.ErrorCode

enum class CategoryErrorCode(
    override val code: Int,
    override val message: String,
) : ErrorCode {
    CATEGORY_NOT_FOUND(404, "해당 ID의 카테고리를 찾을 수 없습니다."),
}
