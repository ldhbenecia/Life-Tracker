package com.benecia.lifetracker.domain.common

import com.benecia.lifetracker.security.userdetails.LoginUser
import java.util.UUID

interface CurrentUserProvider {
    fun getCurrentUserId(): UUID
    fun getCurrentUser(): LoginUser
} 