package com.benecia.lifetracker.todocore.service

import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository {
    fun findByUserIdAndId(userId: UUID, id: Long): Todo
    fun add(todo: Todo): Long
    fun modify(id: Long, todo: Todo): Long
}
