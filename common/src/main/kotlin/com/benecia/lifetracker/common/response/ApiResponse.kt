package com.benecia.lifetracker.common.response

import java.time.LocalDateTime

data class ApiResponse<T>(
    val status: Int,
    val message: String = "success",
    val data: T? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
