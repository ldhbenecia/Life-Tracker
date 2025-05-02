package com.benecia.lifetracker.todo.service

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.common.exception.ErrorCode
import com.benecia.lifetracker.common.service.UserValidationService
import com.benecia.lifetracker.todo.dto.TodoRequest
import com.benecia.lifetracker.todo.dto.TodoResponse
import com.benecia.lifetracker.todo.repository.JdbcTodoRepository
import com.benecia.lifetracker.todo.repository.TodoRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val userValidationService: UserValidationService,
) {

    fun createTodo(request: TodoRequest): TodoResponse {
        userValidationService.validateUserExists(request.userId)

        val todo = request.toEntity()
        val savedTodo = todoRepository.save(todo)
        return TodoResponse.fromEntity(savedTodo)
    }

    fun getTodoById(id: UUID): TodoResponse {
        val todo = todoRepository.findById(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)
        return TodoResponse.fromEntity(todo)
    }

    fun getAllTodos(): List<TodoResponse> {
        return todoRepository.findAll().map { TodoResponse.fromEntity(it) }
    }

    fun getTodosByUserId(userId: UUID): List<TodoResponse> {
        userValidationService.validateUserExists(userId)
        return todoRepository.findAllByUserId(userId).map { TodoResponse.fromEntity(it) }
    }

    fun updateTodo(id: UUID, request: TodoRequest): TodoResponse {
        val existingTodo = todoRepository.findById(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)

        userValidationService.validateUserExists(request.userId)
        if (existingTodo.userId != request.userId) {
            throw CustomException(ErrorCode.UNAUTHORIZED_TODO_ACCESS)
        }

        existingTodo.title = request.title
        existingTodo.description = request.description
        existingTodo.scheduledTime = request.scheduledTime
        existingTodo.isDone = request.isDone

        val updatedTodo = todoRepository.update(existingTodo)
        return TodoResponse.fromEntity(updatedTodo)
    }

    fun toggleTodoDone(id: UUID): TodoResponse {
        val updatedTodo = todoRepository.toggleDone(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)
        return TodoResponse.fromEntity(updatedTodo)
    }
    fun deleteTodo(id: UUID): Boolean {
        todoRepository.findById(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)
        return todoRepository.deleteById(id)
    }
}