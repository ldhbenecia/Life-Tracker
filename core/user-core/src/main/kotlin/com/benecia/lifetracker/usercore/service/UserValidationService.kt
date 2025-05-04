package com.benecia.lifetracker.usercore.service

import java.util.UUID

interface UserValidationService {
    fun validateUserExists(userId: UUID)
}