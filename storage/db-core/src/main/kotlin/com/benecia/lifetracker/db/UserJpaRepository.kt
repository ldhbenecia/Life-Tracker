package com.benecia.lifetracker.db

import org.springframework.data.jpa.repository.JpaRepository

internal interface UserJpaRepository : JpaRepository<UserEntity, Long> {
}