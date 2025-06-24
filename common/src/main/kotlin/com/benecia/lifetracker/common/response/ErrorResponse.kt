package com.benecia.lifetracker.common.response

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: Any,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
