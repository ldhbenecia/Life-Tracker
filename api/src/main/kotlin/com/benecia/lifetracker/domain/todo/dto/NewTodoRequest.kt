package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.command.TodoAddCommand
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class NewTodoRequest(
    val title: String,
    val category: String,
    val scheduledDate: String, // ISO 8601 (예: 2025-07-01T10:00:00)
    val notificationTime: String? = null // ISO 8601 (nullable)
) {
    fun toCommand(userId: UUID = UUID.randomUUID()): TodoAddCommand {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return TodoAddCommand(
            userId = userId,
            title = this.title,
            category = this.category,
            scheduledDate = LocalDateTime.parse(this.scheduledDate, formatter),
            notificationTime = this.notificationTime?.let { LocalDateTime.parse(it, formatter) }
        )
    }
}