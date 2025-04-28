package com.benecia.lifetracker.user.domain

import java.time.LocalDateTime
import java.util.*

class User(
    var id: UUID? = null,
    var username: String,
    var email: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime? = null
)