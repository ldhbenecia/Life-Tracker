package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.info.TodoInfo
import org.springframework.stereotype.Component
import java.util.UUID

@Component
data class TodoReader(
    private val categoryReader: CategoryReader,
    private val todoRepository: TodoRepository,
) {
    fun findById(userId: UUID, id: Long): TodoInfo {
        val todo = todoRepository.findByUserIdAndId(userId, id)
        val category = categoryReader.findByUserIdAndId(userId, todo.categoryId)

        return TodoInfo(
            id = id,
            title = todo.title,
            category = category,
            scheduledDate = todo.scheduledDate,
            notificationTime = todo.notificationTime,
            isDone = todo.isDone,
        )
    }
}
