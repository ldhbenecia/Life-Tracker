package com.benecia.lifetracker.common.exception

import com.benecia.lifetracker.common.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ErrorResponse {
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = mapOf("detail" to (e.message ?: "Unknown error"))
        )
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ErrorResponse {
        return ErrorResponse(
            status = e.errorCode.code,
            error = e.errorCode.name,
            message = mapOf("detail" to e.errorCode.message)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ErrorResponse {
        val fieldErrors = e.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        return ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "VALIDATION_ERROR",
            message = fieldErrors
        )
    }
}