package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Component

@Component
data class UserWriter(
    private val userRepository: UserRepository
) {
    fun add(name: String): Long {
        return userRepository.add(name)
    }
}
