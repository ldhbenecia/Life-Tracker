package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Component
import java.util.UUID

@Component
data class UserWriter(
    private val userRepository: UserRepository
) {
    fun add(user: User): UUID {
        return userRepository.add(user)
    }
}
