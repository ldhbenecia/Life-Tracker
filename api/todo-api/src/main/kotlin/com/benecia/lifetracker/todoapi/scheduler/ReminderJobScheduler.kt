package com.benecia.lifetracker.todoapi.scheduler

import com.benecia.lifetracker.todocore.domain.Todo
import org.quartz.*
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.util.*

@Component
class ReminderJobScheduler(
    private val scheduler: Scheduler
) {

    fun scheduleReminder(todo: Todo) {
        println("💡 Quartz Job 예약: ${todo.title} at ${todo.scheduledTime}")

        val jobDetail = JobBuilder.newJob(TodoReminderJob::class.java)
            .withIdentity(todo.id.toString())
            .usingJobData("todoId", todo.id.toString())
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger-${todo.id}")
            // 한국 시간대로 변경
            .startAt(Date.from(todo.scheduledTime.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
            //.startAt(Date.from(todo.scheduledTime.atZone(ZoneId.of("UTC")).toInstant()))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
            .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }

    fun cancelReminder(todoId: UUID) {
        scheduler.deleteJob(JobKey.jobKey(todoId.toString()))
    }
}