package com.benecia.lifetracker.todoapi.application

import com.benecia.lifetracker.todocore.domain.Todo
import com.benecia.lifetracker.todocore.service.TodoService
import com.benecia.lifetracker.todoapi.scheduler.ReminderJobScheduler
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodoCommandHandler(
    private val todoService: TodoService,
    private val reminderJobScheduler: ReminderJobScheduler
) {
    fun createTodoAndSchedule(todo: Todo): Todo {
        val saved = todoService.createTodo(todo)
        reminderJobScheduler.scheduleReminder(saved)
        return saved
    }

    fun updateTodoAndReschedule(todo: Todo): Todo {
        val updated = todoService.updateTodo(todo)
        reminderJobScheduler.cancelReminder(updated.id!!)
        reminderJobScheduler.scheduleReminder(updated)
        return updated
    }

    fun deleteTodoAndCancel(id: UUID): Boolean {
        reminderJobScheduler.cancelReminder(id)
        return todoService.deleteTodo(id)
    }
}
