package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun read(id: Long): User {
        return userRepository.read(id) ?: throw NoSuchElementException()
    }
}