package com.benecia.lifetracker.domain.user.dto

import com.benecia.lifetracker.user.service.User

data class UserResponse(
    val name: String
) {
    constructor(user: User) : this(user.name)
}
