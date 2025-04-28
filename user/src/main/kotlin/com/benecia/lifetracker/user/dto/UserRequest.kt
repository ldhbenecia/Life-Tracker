package com.benecia.lifetracker.user.dto

import com.benecia.lifetracker.user.domain.User

data class UserRequest(val username: String, val email: String) {

    fun toEntity(): User = User(
        username = username,
        email =  email
    )
}
