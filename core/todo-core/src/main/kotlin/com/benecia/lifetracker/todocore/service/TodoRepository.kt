package com.benecia.lifetracker.todocore.service

import org.springframework.stereotype.Repository

@Repository
interface TodoRepository {
    fun findById(id: Long): Todo
    fun add(todo: Todo): Long
    fun modify(id: Long, todo: Todo): Long
}
