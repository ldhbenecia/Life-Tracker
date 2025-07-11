package com.benecia.lifetracker.todocore.model.info

import java.time.LocalDateTime

data class TodoInfo(
    val id: Long,
    val title: String,
    val category: CategoryInfo,
    val scheduledDate: LocalDateTime,
    val notificationTime: LocalDateTime?,
    val isDone: Boolean,
)
