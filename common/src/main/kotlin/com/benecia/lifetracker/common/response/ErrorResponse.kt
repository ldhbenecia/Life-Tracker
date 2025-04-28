package com.benecia.lifetracker.common.response

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: Map<String, String>,
    val timestamp: LocalDateTime,
) {
    constructor(status: Int, error: String) : this(
        status = status,
        error = error,
        message = emptyMap(),
        timestamp = LocalDateTime.now()
    )

    constructor(status: Int, error: String, message: Map<String, String>) : this(
        status = status,
        error = error,
        message = message,
        timestamp = LocalDateTime.now()
    )
}
