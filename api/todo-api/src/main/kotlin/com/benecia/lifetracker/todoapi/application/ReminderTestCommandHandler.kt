package com.benecia.lifetracker.todoapi.application

import com.benecia.lifetracker.todoapi.scheduler.ReminderJobScheduler
import com.benecia.lifetracker.todocore.domain.Todo
import com.benecia.lifetracker.todocore.service.TodoService
import org.springframework.stereotype.Service

@Service
class ReminderTestCommandHandler(
    private val todoService: TodoService,
    private val reminderJobScheduler: ReminderJobScheduler
) {

    fun createTodoWithReminder(todo: Todo): Todo {
        val saved = todoService.createTodo(todo)
        reminderJobScheduler.scheduleReminder(saved)
        return saved
    }
}