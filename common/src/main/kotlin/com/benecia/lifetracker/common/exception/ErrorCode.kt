package com.benecia.lifetracker.common.exception

enum class ErrorCode(val code: Int, val message: String) {

    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
}