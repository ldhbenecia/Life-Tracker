package com.benecia.lifetracker.todo.repository

import com.benecia.lifetracker.todo.domain.Todo
import java.util.UUID

interface TodoRepository {
    fun save(todo: Todo): Todo
    fun findById(id: UUID): Todo?
    fun findAll(): List<Todo>
    fun findAllByUserId(userId: UUID): List<Todo>
    fun update(todo: Todo): Todo
    fun toggleDone(id: UUID): Todo?
    fun deleteById(id: UUID): Boolean
}