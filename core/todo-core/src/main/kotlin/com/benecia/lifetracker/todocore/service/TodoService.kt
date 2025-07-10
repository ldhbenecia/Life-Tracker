package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.command.ModifyTodo
import com.benecia.lifetracker.todocore.model.command.NewTodo
import com.benecia.lifetracker.todocore.model.info.TodoInfo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodoService(
    private val todoWriter: TodoWriter,
    private val todoReader: TodoReader,
) {
    fun findTodoById(id: Long): TodoInfo {
        return todoReader.findById(id)
    }

    fun addTodo(userId: UUID, command: NewTodo): Long {
        return todoWriter.add(userId, command)
    }

    fun modifyTodo(id: Long, userId: UUID, command: ModifyTodo): Long {
        return todoWriter.modify(id, userId, command)
    }
}
