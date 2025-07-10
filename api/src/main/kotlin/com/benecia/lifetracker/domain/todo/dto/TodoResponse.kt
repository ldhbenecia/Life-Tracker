package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.info.TodoInfo
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.format.DateTimeFormatter

data class TodoResponse(
    val id: Long,
    val title: String,
    val category: String,
    val scheduledDate: String,
    val notificationTime: String?,

    @JsonProperty(value = "isDone")
    val isDone: Boolean,
) {
    companion object {
        private val formatter = DateTimeFormatter.ISO_DATE_TIME
        fun of(info: TodoInfo): TodoResponse = TodoResponse(
            id = info.id,
            title = info.title,
            category = info.category,
            scheduledDate = info.scheduledDate.format(formatter),
            notificationTime = info.notificationTime?.format(formatter),
            isDone = info.isDone,
        )
    }
}
