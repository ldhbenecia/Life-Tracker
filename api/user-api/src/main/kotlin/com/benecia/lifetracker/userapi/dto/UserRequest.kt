package com.benecia.lifetracker.userapi.dto

import com.benecia.lifetracker.usercore.domain.User

data class UserRequest(
    val username: String,
    val email: String
) {
    fun toEntity(): User = User(
        username = username,
        email =  email
    )
}
