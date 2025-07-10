package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.command.ModifyTodo
import com.benecia.lifetracker.todocore.model.command.NewTodo
import org.springframework.stereotype.Component
import java.util.UUID

@Component
data class TodoWriter(
    private val todoReader: TodoReader,
    private val todoRepository: TodoRepository,
) {
    fun add(userId: UUID, command: NewTodo): Long {
        val todo = Todo(
            userId = userId,
            title = command.title,
            category = command.category,
            scheduledDate = command.scheduledDate,
            notificationTime = command.notificationTime,
            isDone = false,
        )

        return todoRepository.add(todo)
    }

    fun modify(id: Long, userId: UUID, command: ModifyTodo): Long {
        val existingTodo = todoReader.findById(id)

        val modifiedTodo = Todo(
            userId = userId,
            title = command.title ?: existingTodo.title,
            category = command.category ?: existingTodo.category,
            scheduledDate = command.scheduledDate ?: existingTodo.scheduledDate,
            notificationTime = command.notificationTime ?: existingTodo.notificationTime,
            isDone = command.isDone ?: existingTodo.isDone,
        )

        return todoRepository.modify(id, modifiedTodo)
    }
}
