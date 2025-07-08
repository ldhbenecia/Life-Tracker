package com.benecia.lifetracker.common.exception

open class CustomException(
    val errorCode: ErrorCode,
    cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause)
