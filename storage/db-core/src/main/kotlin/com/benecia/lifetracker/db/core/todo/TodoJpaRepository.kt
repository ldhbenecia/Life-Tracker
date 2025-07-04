package com.benecia.lifetracker.db.core.todo

import org.springframework.data.jpa.repository.JpaRepository

interface TodoJpaRepository : JpaRepository<TodoEntity, Long> {
}