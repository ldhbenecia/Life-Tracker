package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.command.NewTodo
import java.time.LocalDateTime

data class NewTodoRequest(
    val title: String,
    val category: String,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime? = null,
) {
    fun toNewTodo(): NewTodo {
        return NewTodo(
            title = this.title,
            category = this.category,
            scheduledDate = this.scheduledDate,
            notificationTime = this.notificationTime,
        )
    }
}
