package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.command.TodoAddCommand
import com.benecia.lifetracker.todocore.model.command.TodoModifyCommand
import org.springframework.stereotype.Component

@Component
data class TodoWriter(
    private val todoRepository: TodoRepository
) {
    fun add(command: TodoAddCommand): Long {
        val todo = Todo(
            userId = command.userId,
            title = command.title,
            category = command.category,
            scheduledDate = command.scheduledDate,
            notificationTime = command.notificationTime,
            isDone = false,
        )

        return todoRepository.add(todo)
    }

    fun modify(id: Long, command: TodoModifyCommand) {
        val existingTodo = todoRepository.findById(id)

        val modifiedTodo = Todo(
            userId = existingTodo.userId,
            title = command.title ?: existingTodo.title,
            category = command.category ?: existingTodo.category,
            scheduledDate = command.scheduledDate ?: existingTodo.scheduledDate,
            notificationTime = command.notificationTime ?: existingTodo.notificationTime,
            isDone = command.isDone ?: existingTodo.isDone
        )

        todoRepository.modify(id, modifiedTodo)
    }
}
