package com.benecia.lifetracker.todoapi.controller

import com.benecia.lifetracker.todoapi.dto.TodoRequest
import com.benecia.lifetracker.todoapi.dto.TodoResponse
import com.benecia.lifetracker.todocore.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/todos")
class TodoController(private val todoService: TodoService) {

    @PostMapping
    fun createTodo(@RequestBody request: TodoRequest): ResponseEntity<TodoResponse> {
        val todo = request.toEntity()
        val saved = todoService.createTodo(todo)
        return ResponseEntity.status(HttpStatus.CREATED).body(TodoResponse.fromEntity(saved))
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: UUID): ResponseEntity<TodoResponse> {
        val todo = todoService.getTodoById(id)
        return ResponseEntity.ok(TodoResponse.fromEntity(todo))
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<TodoResponse>> {
        val todos = todoService.getAllTodos()
        return ResponseEntity.ok(todos.map { TodoResponse.fromEntity(it) })
    }

    @GetMapping("/user/{userId}")
    fun getTodosByUserId(@PathVariable userId: UUID): ResponseEntity<List<TodoResponse>> {
        val todos = todoService.getTodosByUserId(userId)
        return ResponseEntity.ok(todos.map { TodoResponse.fromEntity(it) })
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: UUID,
        @RequestBody request: TodoRequest
    ): ResponseEntity<TodoResponse> {
        val todo = request.toEntity().apply { this.id = id }
        val updated = todoService.updateTodo(todo)
        return ResponseEntity.ok(TodoResponse.fromEntity(updated))
    }

    @PatchMapping("/{id}/toggle-done")
    fun toggleTodoDone(@PathVariable id: UUID): ResponseEntity<TodoResponse> {
        val updated = todoService.toggleTodoDone(id)
        return ResponseEntity.ok(TodoResponse.fromEntity(updated))
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = todoService.deleteTodo(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}