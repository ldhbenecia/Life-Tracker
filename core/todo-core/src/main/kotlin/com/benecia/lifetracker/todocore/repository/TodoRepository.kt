package com.benecia.lifetracker.todocore.repository

import com.benecia.lifetracker.todocore.domain.Todo
import com.benecia.lifetracker.todocore.model.DailyTodoStats
import java.time.LocalDateTime
import java.util.*

interface TodoRepository {
    fun save(todo: Todo): Todo
    fun findById(id: UUID): Todo?
    fun findAll(): List<Todo>
    fun findAllByUserId(userId: UUID): List<Todo>
    fun update(todo: Todo): Todo
    fun toggleDone(id: UUID): Todo?
    fun deleteById(id: UUID): Boolean
    fun countAllByUserId(userId: UUID): Int
    fun countDoneByUserId(userId: UUID): Int
    fun findDoneCountGroupedByDate(userId: UUID): List<DailyTodoStats>
    fun findTodosToRemind(now: LocalDateTime): List<Todo>
}