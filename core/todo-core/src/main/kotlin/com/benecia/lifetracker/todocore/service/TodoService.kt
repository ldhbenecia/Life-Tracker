package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.command.TodoAddCommand
import com.benecia.lifetracker.todocore.model.command.TodoModifyCommand
import com.benecia.lifetracker.todocore.model.info.TodoInfo
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoWriter: TodoWriter,
    private val todoReader: TodoReader
) {
    fun readTodoById(id: Long): TodoInfo {
        return todoReader.readById(id)
    }

    fun addTodo(command: TodoAddCommand): Long {
        return todoWriter.add(command)
    }

    fun modifyTodo(id: Long, command: TodoModifyCommand) {
        return todoWriter.modify(id, command)
    }
}