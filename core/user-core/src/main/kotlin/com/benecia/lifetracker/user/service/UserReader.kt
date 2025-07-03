package com.benecia.lifetracker.user.service

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.user.exception.UserErrorCode
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun read(id: UUID): User {
        return userRepository.read(id) ?: throw CustomException(UserErrorCode.USER_NOT_FOUND)
    }
}