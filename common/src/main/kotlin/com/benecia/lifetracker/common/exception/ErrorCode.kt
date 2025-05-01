package com.benecia.lifetracker.common.exception

enum class ErrorCode(val code: Int, val message: String) {

    // Default
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // User
    USER_NOT_FOUND(404, "User not found"),
    USER_ALREADY_EXISTS(409, "User already exists"),
    INVALID_USER_INPUT(400, "Invalid user input")
}