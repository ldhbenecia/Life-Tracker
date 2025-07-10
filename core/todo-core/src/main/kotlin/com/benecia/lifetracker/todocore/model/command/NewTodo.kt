package com.benecia.lifetracker.todocore.model.command

import java.time.LocalDateTime

data class NewTodo(
    val title: String,
    val category: String,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime? = null,
)
