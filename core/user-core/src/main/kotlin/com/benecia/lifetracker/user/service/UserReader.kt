package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun read(id: UUID): User {
        return userRepository.read(id) ?: throw NoSuchElementException()
    }
}