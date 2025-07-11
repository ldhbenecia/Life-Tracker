package com.benecia.lifetracker.todocore.service

import java.time.LocalDateTime
import java.util.UUID

class Todo(
    val id: Long? = null,
    val userId: UUID,
    val categoryId: Long,
    val title: String,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime?,
    val isDone: Boolean,
)
