package com.benecia.lifetracker.todocore.model

import java.time.LocalDate

data class DailyTodoStats(
    val date: LocalDate,
    val doneCount: Int
)
