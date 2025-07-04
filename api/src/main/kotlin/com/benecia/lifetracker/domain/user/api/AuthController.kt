package com.benecia.lifetracker.domain.user.api

import com.benecia.lifetracker.auth.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @GetMapping("/{provider}/callback")
    fun handleOAuthCallback(
        @PathVariable provider: String,
        @RequestParam code: String,
        @RequestParam(required = false) error: String?,
        @RequestParam(required = false) state: String?,
    ) {
        logger.info("Received {} OAuth callback - code: {}, error: {}, state: {}",
            provider.uppercase(), code, error, state)

        authService.handleOAuthCallback(
            provider = provider.lowercase(),
            code = code,
            error = error,
            state = state,
        )
    }
}