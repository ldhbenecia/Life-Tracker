package com.benecia.lifetracker.todocore.model.command

import java.time.LocalDateTime

data class ModifyTodo(
    val title: String? = null,
    val category: String? = null,
    val scheduledDate: LocalDateTime? = null,
    val notificationTime: LocalDateTime? = null,
    val isDone: Boolean? = null,
)
