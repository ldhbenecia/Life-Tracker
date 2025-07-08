package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Component
import java.util.*

@Component
class UserReader(
    private val userRepository: UserRepository,
) {
    fun findById(id: UUID): User? {
        return userRepository.findById(id)
    }

    fun findByProviderAndEmail(provider: String, email: String): User? {
        return userRepository.findByProviderAndEmail(provider, email)
    }
}
