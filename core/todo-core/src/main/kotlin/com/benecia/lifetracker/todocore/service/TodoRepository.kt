package com.benecia.lifetracker.todocore.service

interface TodoRepository {
    fun add(todo: Todo): Long
    fun findById(id: Long): Todo
    fun modify(id: Long, todo: Todo)
}