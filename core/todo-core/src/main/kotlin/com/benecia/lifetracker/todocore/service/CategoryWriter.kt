package com.benecia.lifetracker.todocore.service

import org.springframework.stereotype.Component

@Component
data class CategoryWriter(
    private val categoryReader: CategoryReader,
    private val categoryRepository: CategoryRepository,
)
