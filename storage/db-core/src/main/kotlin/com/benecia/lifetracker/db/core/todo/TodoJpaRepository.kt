package com.benecia.lifetracker.db.core.todo

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TodoJpaRepository : JpaRepository<TodoEntity, Long> {
    fun findByUserIdAndId(userId: UUID, id: Long): TodoEntity?
}
