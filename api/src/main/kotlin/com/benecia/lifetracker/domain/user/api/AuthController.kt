package com.benecia.lifetracker.domain.user.api

import com.benecia.lifetracker.auth.AuthService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class AuthController(
    private val authService: AuthService,
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @GetMapping("/api/auth/{provider}/callback")
    fun handleOAuthCallback(
        @PathVariable provider: String,
        @RequestParam code: String,
        @RequestParam(required = false) error: String?,
        @RequestParam(required = false) state: String?,
    ): ResponseEntity<Void> {
        logger.info("Received {} OAuth callback - code: {}, error: {}, state: {}",
            provider.uppercase(), code, error, state)

        val redirectUrl = authService.handleOAuthCallback(
            provider = provider.lowercase(),
            code = code,
            error = error,
            state = state,
        )

        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(redirectUrl))
            .build()
    }
}