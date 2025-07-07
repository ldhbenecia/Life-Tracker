package com.benecia.lifetracker.domain.todo.api

import com.benecia.lifetracker.common.response.ApiResponse
import com.benecia.lifetracker.domain.common.CurrentUserProvider
import com.benecia.lifetracker.domain.todo.dto.ModifyTodoRequest
import com.benecia.lifetracker.domain.todo.dto.NewTodoRequest
import com.benecia.lifetracker.domain.todo.dto.TodoResponse
import com.benecia.lifetracker.todocore.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/todos")
class TodoController(
    private val todoService: TodoService,
    private val currentUserProvider: CurrentUserProvider
) {
    @GetMapping("/{id}")
    fun readTodo(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<TodoResponse>> {
        val todoInfo = todoService.readTodoById(id)
        return ResponseEntity.ok(ApiResponse.success(TodoResponse.from(todoInfo)))
    }

    @PostMapping
    fun addTodo(
        @RequestBody request: NewTodoRequest
    ): ResponseEntity<ApiResponse<Long>> {
        val command = request.toCommand(currentUserProvider.getCurrentUserId())
        val todoId = todoService.addTodo(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(todoId))
    }

    @PutMapping("/{id}")
    fun modifyTodo(
        @PathVariable id: Long,
        @RequestBody request: ModifyTodoRequest
    ): ResponseEntity<ApiResponse<Long>> {
        val command = request.toCommand(currentUserProvider.getCurrentUserId())
        println("todoService = ${command}")
        todoService.modifyTodo(id, command)
        return ResponseEntity.ok(ApiResponse.success(id))
    }
}