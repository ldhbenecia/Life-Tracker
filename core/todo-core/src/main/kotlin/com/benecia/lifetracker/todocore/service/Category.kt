package com.benecia.lifetracker.todocore.service

import java.util.UUID

data class Category(
    val id: Long? = null,
    val userId: UUID,
    val name: String,
    val icon: String,
    val color: String,
)
