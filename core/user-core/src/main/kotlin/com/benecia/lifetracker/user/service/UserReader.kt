package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Component
import java.util.*

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun read(id: UUID): User {
        return userRepository.read(id)
    }
}