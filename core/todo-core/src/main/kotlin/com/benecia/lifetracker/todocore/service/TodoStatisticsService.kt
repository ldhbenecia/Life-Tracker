package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.DailyTodoStats
import com.benecia.lifetracker.todocore.repository.TodoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TodoStatisticsService(
    private val todoRepository: TodoRepository
) {

    fun getLast7DaysDoneStats(userId: UUID): List<DailyTodoStats> {
        return todoRepository.findDoneCountGroupedByDate(userId)
    }
}