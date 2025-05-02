package com.benecia.lifetracker.usercore.domain

import java.time.LocalDateTime
import java.util.*

class User(
    var id: UUID? = null,
    var username: String,
    var email: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime? = null
) {
    fun withId(id: UUID): User {
        this.id = id
        return this
    }

    fun updateInfo(username: String, email: String) {
        this.username = username
        this.email = email
        this.updatedAt = LocalDateTime.now()
    }
}