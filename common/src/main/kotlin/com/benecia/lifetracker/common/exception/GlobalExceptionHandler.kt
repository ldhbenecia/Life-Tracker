package com.benecia.lifetracker.common.exception

import com.benecia.lifetracker.common.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unhandled Exception: ", e)
        val errorResponse = ErrorResponse(
            status = 500,
            error = "INTERNAL_SERVER_ERROR",
            message = "서버 내부에서 예상치 못한 오류가 발생했습니다."
        )
        return ResponseEntity.internalServerError().body(errorResponse)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        val errorResponse = ErrorResponse(
            status = errorCode.code,
            error = errorCode.name,
            message = errorCode.message
        )
        log.warn("CustomException: code=${errorCode.code}, name=${errorCode.name}, message=${errorCode.message}")
        return ResponseEntity.status(errorCode.code).body(errorResponse)
    }
}