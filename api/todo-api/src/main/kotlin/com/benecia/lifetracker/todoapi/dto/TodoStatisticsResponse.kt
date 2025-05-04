package com.benecia.lifetracker.todoapi.dto

data class TodoStatisticsResponse(
    val totalCount: Int,
    val doneCount: Int,
    val completionRate: Double
)
