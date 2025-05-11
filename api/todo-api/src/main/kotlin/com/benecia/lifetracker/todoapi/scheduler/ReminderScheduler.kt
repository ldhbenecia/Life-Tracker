package com.benecia.lifetracker.todoapi.scheduler

import com.benecia.lifetracker.todocore.repository.TodoRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReminderScheduler(
    private val todoRepository: TodoRepository
) {

    @Scheduled(fixedRate = 60_000)
    fun checkTodosForReminder() {
        val now = LocalDateTime.now()
        val todosToRemind = todoRepository.findTodosToRemind(now)

        todosToRemind.forEach { todo ->
            println("🔔 [알림] 할 일 '${todo.title}' (유저 ID: ${todo.userId}) 의 시간이 되었습니다.")
        }
    }
}