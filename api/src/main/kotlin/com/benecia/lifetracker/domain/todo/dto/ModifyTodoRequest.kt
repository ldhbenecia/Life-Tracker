package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.command.ModifyTodo
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ModifyTodoRequest(
    val title: String? = null,
    val category: String? = null,
    val scheduledDate: LocalDateTime? = null,
    val notificationTime: LocalDateTime? = null,

    @JsonProperty(value = "isDone")
    val isDone: Boolean? = null,
) {
    fun toModifyTodo(): ModifyTodo {
        return ModifyTodo(
            title = this.title,
            category = this.category,
            scheduledDate = this.scheduledDate,
            notificationTime = this.notificationTime,
            isDone = this.isDone,
        )
    }
}
