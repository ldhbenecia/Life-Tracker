package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.common.exception.ErrorCode
import com.benecia.lifetracker.todocore.domain.Todo
import com.benecia.lifetracker.todocore.repository.TodoRepository
import com.benecia.lifetracker.usercore.service.UserValidationService
import org.springframework.stereotype.Service
import java.util.*

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val userValidationService: UserValidationService,
) {

    fun createTodo(todo: Todo): Todo {
        userValidationService.validateUserExists(todo.userId)
        return todoRepository.save(todo)
    }

    fun getTodoById(id: UUID): Todo {
        return todoRepository.findById(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)
    }

    fun getAllTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    fun getTodosByUserId(userId: UUID): List<Todo> {
        userValidationService.validateUserExists(userId)
        return todoRepository.findAllByUserId(userId)
    }

    fun updateTodo(todo: Todo): Todo {
        val existing = todoRepository.findById(todo.id!!)
            ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)

        userValidationService.validateUserExists(todo.userId)
        if (existing.userId != todo.userId) {
            throw CustomException(ErrorCode.UNAUTHORIZED_TODO_ACCESS)
        }

        existing.updateFrom(todo)
        return todoRepository.update(existing)
    }

    fun toggleTodoDone(id: UUID): Todo {
        return todoRepository.toggleDone(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)
    }

    fun deleteTodo(id: UUID): Boolean {
        todoRepository.findById(id) ?: throw CustomException(ErrorCode.TODO_NOT_FOUND)
        return todoRepository.deleteById(id)
    }

    fun getStatisticsByUser(userId: UUID): Pair<Int, Int> {
        userValidationService.validateUserExists(userId)

        val totalCount = todoRepository.countAllByUserId(userId)
        val doneCount = todoRepository.countDoneByUserId(userId)
        return Pair(totalCount, doneCount)
    }
}