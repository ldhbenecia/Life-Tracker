package com.benecia.lifetracker.todoapi.controller

import com.benecia.lifetracker.common.response.ApiResponse
import com.benecia.lifetracker.todoapi.dto.TodoRequest
import com.benecia.lifetracker.todoapi.dto.TodoResponse
import com.benecia.lifetracker.todoapi.dto.TodoStatisticsResponse
import com.benecia.lifetracker.todocore.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/todos")
class TodoController(private val todoService: TodoService) {

    @PostMapping
    fun createTodo(@RequestBody @Valid request: TodoRequest): ResponseEntity<ApiResponse<TodoResponse>> {
        val todo = request.toEntity()
        val saved = todoService.createTodo(todo)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse(201, "Todo created", TodoResponse.fromEntity(saved)))
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: UUID): ResponseEntity<ApiResponse<TodoResponse>> {
        val todo = todoService.getTodoById(id)
        return ResponseEntity.ok(ApiResponse(200, data = TodoResponse.fromEntity(todo)))
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<ApiResponse<List<TodoResponse>>> {
        val todos = todoService.getAllTodos().map { TodoResponse.fromEntity(it) }
        return ResponseEntity.ok(ApiResponse(200, data = todos))
    }

    @GetMapping("/user/{userId}")
    fun getTodosByUserId(@PathVariable userId: UUID): ResponseEntity<ApiResponse<List<TodoResponse>>> {
        val todos = todoService.getTodosByUserId(userId).map { TodoResponse.fromEntity(it) }
        return ResponseEntity.ok(ApiResponse(200, data = todos))
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: UUID,
        @RequestBody request: TodoRequest
    ): ResponseEntity<ApiResponse<TodoResponse>> {
        val updated = todoService.updateTodo(request.toEntity().apply { this.id = id })
        return ResponseEntity.ok(ApiResponse(200, "Todo updated", TodoResponse.fromEntity(updated)))
    }

    @PatchMapping("/{id}/toggle-done")
    fun toggleTodoDone(@PathVariable id: UUID): ResponseEntity<ApiResponse<TodoResponse>> {
        val updated = todoService.toggleTodoDone(id)
        return ResponseEntity.ok(ApiResponse(200, "Toggled done", TodoResponse.fromEntity(updated)))
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: UUID): ResponseEntity<ApiResponse<Void>> {
        val deleted = todoService.deleteTodo(id)
        return if (deleted) {
            ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse(204, "Todo deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(404, "Todo not found"))
        }
    }

    @GetMapping("/users/{userId}/statistics")
    fun getTodoStatistics(@PathVariable userId: UUID): ResponseEntity<ApiResponse<TodoStatisticsResponse>> {
        val (totalCount, doneCount) = todoService.getStatisticsByUser(userId)
        val completionRate = if (totalCount > 0) doneCount.toDouble() / totalCount else 0.0
        val response = TodoStatisticsResponse(totalCount, doneCount, completionRate)
        return ResponseEntity.ok(ApiResponse(200, "Statistics fetched", response))
    }
}