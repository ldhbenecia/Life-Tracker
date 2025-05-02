package com.benecia.lifetracker.user.service

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.common.exception.ErrorCode
import com.benecia.lifetracker.common.service.UserValidationService
import com.benecia.lifetracker.user.repository.JdbcUserRepository
import com.benecia.lifetracker.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserValidationServiceImpl(private val userRepository: UserRepository): UserValidationService {
    override fun validateUserExists(userId: UUID) {
        userRepository.findById(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

}