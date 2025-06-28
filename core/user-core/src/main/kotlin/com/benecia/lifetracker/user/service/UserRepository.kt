package com.benecia.lifetracker.user.service

import java.util.UUID

interface UserRepository {
    fun add(user: User): UUID
    fun read(id: UUID): User?
}