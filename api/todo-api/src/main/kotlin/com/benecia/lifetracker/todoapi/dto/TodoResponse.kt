package com.benecia.lifetracker.todoapi.dto

import com.benecia.lifetracker.todocore.domain.Todo
import java.time.LocalDateTime
import java.util.UUID

data class TodoResponse(
    val id: UUID?,
    val userId: UUID,
    val title: String,
    val description: String,
    val scheduledTime: LocalDateTime,
    val isDone: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(todo: Todo): TodoResponse = TodoResponse(
            id = todo.id,
            userId = todo.userId,
            title = todo.title,
            description = todo.description,
            scheduledTime = todo.scheduledTime,
            isDone = todo.isDone,
            createdAt = todo.createdAt
        )
    }
}
