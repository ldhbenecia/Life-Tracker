package com.benecia.lifetracker.user.service

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.common.exception.ErrorCode
import com.benecia.lifetracker.user.dto.UserRequest
import com.benecia.lifetracker.user.dto.UserResponse
import com.benecia.lifetracker.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(request: UserRequest): UserResponse {
        userRepository.findByUsername(request.username)?.let {
            throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = request.toEntity()
        val savedUser = userRepository.save(user)
        return UserResponse.fromEntity(savedUser)
    }

    fun getUserById(id: UUID): UserResponse {
        val user = userRepository.findById(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return UserResponse.fromEntity(user)
    }

    fun getUserByUsername(username: String): UserResponse {
        val user = userRepository.findByUsername(username) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return UserResponse.fromEntity(user)
    }

    fun getAllUsers(): List<UserResponse> {
        return userRepository.findByAll().map { UserResponse.fromEntity(it) }
    }

    fun updateUser(id: UUID, request: UserRequest): UserResponse {
        val existingUser = userRepository.findById(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        userRepository.findByUsername(request.username)?.let {
            if (it.id == existingUser.id) {
                throw CustomException(ErrorCode.USER_ALREADY_EXISTS)
            }
        }

        existingUser.username = request.username
        existingUser.email = request.email

        val updatedUser = userRepository.save(existingUser)
        return UserResponse.fromEntity(updatedUser)
    }

    fun softDeleteUserById(id: UUID) {
        userRepository.findById(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        userRepository.softDeleteById(id)
    }
}