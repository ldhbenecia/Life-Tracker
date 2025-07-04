package com.benecia.lifetracker.todocore.model.command

import java.time.LocalDateTime
import java.util.UUID

data class TodoModifyCommand(
    val userId: UUID,
    val title: String? = null,
    val category: String? = null,
    val scheduledDate: LocalDateTime? = null,
    val notificationTime: LocalDateTime? = null,
    val isDone: Boolean? = null,
)
