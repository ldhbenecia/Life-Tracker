package com.benecia.lifetracker.user.repository

import com.benecia.lifetracker.user.domain.User
import java.util.UUID

interface UserRepository {

    fun save(user: User): User
    fun findById(id: UUID): User?
    fun findByUsername(username: String): User?
    fun findByAll(): List<User>
    fun update(user: User): User
    fun softDeleteById(id: UUID)
}