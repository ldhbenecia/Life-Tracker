package com.benecia.lifetracker.todoapi.scheduler

import com.benecia.lifetracker.todocore.repository.TodoRepository
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TodoReminderJob : Job {

    @Autowired
    private lateinit var todoRepository: TodoRepository

    override fun execute(context: JobExecutionContext) {
        println("🚀 [Quartz 실행됨] 알림 Job 트리거됨")

        val jobDataMap = context.mergedJobDataMap
        val todoId = UUID.fromString(jobDataMap.getString("todoId"))

        val todo = todoRepository.findById(todoId)

        if (todo != null && !todo.isDone) {
            println("🔔 [Quartz 알림] '${todo.title}' (유저 ID: ${todo.userId}) 정확히 ${todo.scheduledTime}에 실행")
        } else {
            println("⚠️ 알림 대상 Todo가 없음 또는 이미 완료됨")
        }
    }
}