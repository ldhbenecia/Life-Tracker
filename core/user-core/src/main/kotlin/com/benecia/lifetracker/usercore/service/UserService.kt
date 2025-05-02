package com.benecia.lifetracker.usercore.service

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.common.exception.ErrorCode
import com.benecia.lifetracker.usercore.domain.User
import com.benecia.lifetracker.usercore.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User): User {
        userRepository.findByUsername(user.username)?.let {
            throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
        }
        return userRepository.save(user)
    }

    fun getUserById(id: UUID): User {
        return userRepository.findById(id)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findByAll()
    }

    fun updateUser(user: User): User {
        val existing = userRepository.findById(user.id!!)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        userRepository.findByUsername(user.username)?.let {
            if (it.id != user.id) {
                throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
            }
        }

        existing.updateInfo(user.username, user.email)
        return userRepository.save(existing)
    }

    fun softDeleteUserById(id: UUID) {
        userRepository.findById(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        userRepository.softDeleteById(id)
    }
}