package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.command.TodoAddCommand
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class NewTodoRequest(
    val title: String,
    val category: String,
    val scheduledDate: String,
    val notificationTime: String? = null,
) {
    fun toCommand(userId: UUID = UUID.randomUUID()): TodoAddCommand {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return TodoAddCommand(
            userId = userId,
            title = this.title,
            category = this.category,
            scheduledDate = LocalDateTime.parse(this.scheduledDate, formatter),
            notificationTime = this.notificationTime?.let { LocalDateTime.parse(it, formatter) },
        )
    }
}
