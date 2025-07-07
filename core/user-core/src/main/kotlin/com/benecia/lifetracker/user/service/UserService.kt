package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Service
import java.util.UUID


@Service
class UserService(
    private val userWriter: UserWriter,
    private val userReader: UserReader
) {
    fun add(user: User): User {
        val userId = userWriter.add(user)
        return user.copy(id = userId)
    }

    fun findById(id: UUID): User? {
        return userReader.findById(id)
    }

    fun findByProviderAndEmail(provider: String, email: String): User? {
        return userReader.findByProviderAndEmail(provider, email)
    }

    fun update(user: User): User {
        return userWriter.update(user)
    }
}