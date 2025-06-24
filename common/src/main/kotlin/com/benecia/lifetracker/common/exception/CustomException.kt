package com.benecia.lifetracker.common.exception

open class CustomException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)