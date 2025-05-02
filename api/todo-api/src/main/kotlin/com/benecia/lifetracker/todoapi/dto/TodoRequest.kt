package com.benecia.lifetracker.todoapi.dto

import com.benecia.lifetracker.todocore.domain.Todo
import java.time.LocalDateTime
import java.util.UUID

data class TodoRequest(
    val userId: UUID,
    val title: String,
    val description: String,
    val scheduledTime: LocalDateTime,
    val isDone: Boolean = false
) {
    fun toEntity(): Todo = Todo(
        userId = userId,
        title = title,
        description = description,
        scheduledTime = scheduledTime,
        isDone = isDone
    )
}
