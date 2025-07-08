package com.benecia.lifetracker.common.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T?,
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun <T> success(data: T? = null): ApiResponse<T> {
            return ApiResponse(
                status = HttpStatus.OK.value(),
                message = "success",
                data = data,
            )
        }

        fun <T> created(data: T? = null): ApiResponse<T> {
            return ApiResponse(
                status = HttpStatus.CREATED.value(),
                message = "created",
                data = data,
            )
        }
    }
}
