package com.benecia.lifetracker.todocore.service

import java.time.LocalDateTime
import java.util.*

class Todo(
    val id: Long? = null,
    val userId: UUID,
    val title: String,
    val category: String,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime?,
    val isDone: Boolean,
)
