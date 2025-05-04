package com.benecia.lifetracker.todoapi.dto

import com.benecia.lifetracker.todocore.domain.Todo
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class TodoRequest(
    @field:NotNull(message = "User ID is required")
    val userId: UUID,

    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotBlank(message = "Description is required")
    val description: String,

    @field:Future(message = "Scheduled time must be in the future")
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
