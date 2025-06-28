package com.benecia.lifetracker.user.service

import java.util.UUID

class User(
    val id: UUID,
    val provider: String,
    val email: String,
    val displayName: String,
    val profileImageUrl: String?,
)