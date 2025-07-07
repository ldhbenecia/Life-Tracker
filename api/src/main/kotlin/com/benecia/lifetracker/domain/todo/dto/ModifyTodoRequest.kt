package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.command.TodoModifyCommand
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ModifyTodoRequest(
    val title: String? = null,
    val category: String? = null,
    val scheduledDate: String? = null, // ISO 8601 (예: 2025-07-01T10:00:00)
    val notificationTime: String? = null, // ISO 8601 (nullable)

    @JsonProperty(value = "isDone")
    val isDone: Boolean? = null
) {
    fun toCommand(userId: UUID): TodoModifyCommand {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return TodoModifyCommand(
            userId = userId,
            title = this.title,
            category = this.category,
            scheduledDate = this.scheduledDate?.let { LocalDateTime.parse(it, formatter) },
            notificationTime = this.notificationTime?.let { LocalDateTime.parse(it, formatter) },
            isDone = this.isDone
        )
    }
}
