package com.benecia.lifetracker.todocore.model.command

import java.time.LocalDateTime
import java.util.UUID

data class TodoAddCommand(
    val userId: UUID,
    val title: String,
    val category: String,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime? = null,
)
