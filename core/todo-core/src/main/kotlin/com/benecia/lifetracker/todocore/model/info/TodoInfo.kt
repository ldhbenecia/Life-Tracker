package com.benecia.lifetracker.todocore.model.info

import java.time.LocalDateTime
import java.util.UUID

data class TodoInfo(
    val id: Long,
    val userId: UUID,
    val title: String,
    val category: String,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime?,
    val isDone: Boolean
)
