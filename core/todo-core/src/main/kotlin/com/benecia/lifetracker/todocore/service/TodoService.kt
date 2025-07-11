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
    fun findTodoById(userId: UUID, id: Long): TodoInfo {
        return todoReader.findById(userId, id)
    }

    fun addTodo(userId: UUID, command: NewTodo): Long {
        return todoWriter.add(userId, command)
    }

    fun modifyTodo(userId: UUID, id: Long, command: ModifyTodo): Long {
        return todoWriter.modify(userId, id, command)
    }
}
