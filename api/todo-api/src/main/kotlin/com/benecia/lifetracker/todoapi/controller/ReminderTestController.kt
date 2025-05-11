package com.benecia.lifetracker.todoapi.controller

import com.benecia.lifetracker.common.response.ApiResponse
import com.benecia.lifetracker.todoapi.application.ReminderTestCommandHandler
import com.benecia.lifetracker.todoapi.dto.TodoRequest
import com.benecia.lifetracker.todoapi.dto.TodoResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/todos-test")
class ReminderTestController(
    private val reminderTestCommandHandler: ReminderTestCommandHandler
) {

    @PostMapping()
    fun createTodoWithReminder(
        @RequestBody @Valid request: TodoRequest
    ): ResponseEntity<ApiResponse<TodoResponse>> {
        val todo = request.toEntity()
        val saved = reminderTestCommandHandler.createTodoWithReminder(todo)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse(201,"Test Todo created with reminder", TodoResponse.fromEntity(saved)))
    }
}