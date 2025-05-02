package com.benecia.lifetracker.usercore.repository

import com.benecia.lifetracker.usercore.domain.User
import java.util.*

interface UserRepository {

    fun save(user: User): User
    fun findById(id: UUID): User?
    fun findByUsername(username: String): User?
    fun findByAll(): List<User>
    fun update(user: User): User
    fun softDeleteById(id: UUID)
}