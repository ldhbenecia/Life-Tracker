package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository {
    fun add(user: User): UUID
    fun read(id: UUID): User
}