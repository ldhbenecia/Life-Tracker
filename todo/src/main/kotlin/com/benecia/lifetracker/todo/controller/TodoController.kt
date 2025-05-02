package com.benecia.lifetracker.todo.controller

import com.benecia.lifetracker.todo.dto.TodoRequest
import com.benecia.lifetracker.todo.dto.TodoResponse
import com.benecia.lifetracker.todo.service.TodoService
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
        val response = todoService.createTodo(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: UUID): ResponseEntity<TodoResponse> {
        val response = todoService.getTodoById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<TodoResponse>> {
        val responses = todoService.getAllTodos()
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/user/{userId}")
    fun getTodosByUserId(@PathVariable userId: UUID): ResponseEntity<List<TodoResponse>> {
        val responses = todoService.getTodosByUserId(userId)
        return ResponseEntity.ok(responses)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: UUID,
        @RequestBody request: TodoRequest
    ): ResponseEntity<TodoResponse> {
        val response = todoService.updateTodo(id, request)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/toggle-done")
    fun toggleTodoDone(@PathVariable id: UUID): ResponseEntity<TodoResponse> {
        val response = todoService.toggleTodoDone(id)
        return ResponseEntity.ok(response)
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