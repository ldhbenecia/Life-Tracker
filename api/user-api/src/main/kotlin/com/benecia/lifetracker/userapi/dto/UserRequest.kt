package com.benecia.lifetracker.userapi.dto

import com.benecia.lifetracker.usercore.domain.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequest(
    @field:NotBlank(message = "Username must not be blank")
    val username: String,

    @field:Email(message = "Invalid email format")
    val email: String
) {
    fun toEntity(): User = User(
        username = username,
        email =  email
    )
}
