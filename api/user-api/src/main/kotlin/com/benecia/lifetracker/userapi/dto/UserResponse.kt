package com.benecia.lifetracker.userapi.dto

import com.benecia.lifetracker.usercore.domain.User
import java.time.LocalDateTime
import java.util.*

data class UserResponse(
    val id: UUID,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    // 정적 팩토리 메서드 구현
    // fromEntity 메서드는 User 엔티티를 UserResponse DTO로 변환하는 역할
    companion object {
        fun fromEntity(user: User): UserResponse = UserResponse(
            id = requireNotNull(user.id),
            username = user.username,
            email = user.email,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}
