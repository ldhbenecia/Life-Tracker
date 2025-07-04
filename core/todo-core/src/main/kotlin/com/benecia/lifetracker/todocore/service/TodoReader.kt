package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.info.TodoInfo
import org.springframework.stereotype.Component

@Component
data class TodoReader(
    private val todoRepository: TodoRepository
) {
    fun readById(id: Long): TodoInfo {
        val todo = todoRepository.findById(id)

        return TodoInfo(
            id = id,
            userId = todo.userId,
            title = todo.title,
            category = todo.category,
            scheduledDate = todo.scheduledDate,
            notificationTime = todo.notificationTime,
            isDone = todo.isDone
        )
    }
}
