package com.benecia.lifetracker.common.exception

open class CoreException(
    val errorCode: ErrorCode,
    cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause)
