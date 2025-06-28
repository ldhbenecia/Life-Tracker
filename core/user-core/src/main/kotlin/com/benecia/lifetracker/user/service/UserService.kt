package com.benecia.lifetracker.user.service

import org.springframework.stereotype.Service
import java.util.UUID


@Service
class UserService(
    private val userWriter: UserWriter,
    private val userReader: UserReader
) {
    fun add(user: User): UUID {
        return userWriter.add(user)
    }

    fun read(id: UUID): User {
        return userReader.read(id)
    }
}