package com.benecia.lifetracker.common.service

import java.util.UUID

interface UserValidationService {
    fun validateUserExists(userId: UUID)
}