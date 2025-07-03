package com.benecia.lifetracker.db.core.user

import com.benecia.lifetracker.user.service.User
import com.benecia.lifetracker.user.service.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserEntityRepository(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun add(user: User): UUID {
        val entity = UserEntity(
            provider = user.provider,
            email = user.email,
            displayName = user.displayName,
            profileImageUrl = user.profileImageUrl,
        )
        return userJpaRepository.save(entity).id!!
    }

    override fun read(id: UUID): User? {
        return userJpaRepository.findByIdOrNull(id)?.let {
            User(
                id = it.id!!,
                provider = it.provider,
                email = it.email,
                displayName = it.displayName,
                profileImageUrl = it.profileImageUrl,
            )
        }
    }
}