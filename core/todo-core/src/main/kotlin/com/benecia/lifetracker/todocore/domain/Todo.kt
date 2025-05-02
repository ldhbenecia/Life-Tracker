package com.benecia.lifetracker.todocore.domain

import java.time.LocalDateTime
import java.util.*

data class Todo(
    var id: UUID? = null,
    var userId: UUID,
    var title: String,
    var description: String,
    var scheduledTime: LocalDateTime,
    var isDone: Boolean = false,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun updateFrom(source: Todo) {
        this.title = source.title
        this.description = source.description
        this.scheduledTime = source.scheduledTime
        this.isDone = source.isDone
    }
}