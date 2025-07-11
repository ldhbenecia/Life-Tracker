package com.benecia.lifetracker.todocore.service

import com.benecia.lifetracker.todocore.model.info.CategoryInfo
import org.springframework.stereotype.Component
import java.util.UUID

@Component
data class CategoryReader(
    private val categoryRepository: CategoryRepository,
) {
    fun findByUserIdAndId(userId: UUID, id: Long): CategoryInfo {
        val category = categoryRepository.findByUserIdAndId(userId, id)

        return CategoryInfo(
            id = id,
            name = category.name,
            icon = category.icon,
            color = category.color,
        )
    }

    fun findByUserIdAndName(userId: UUID, name: String): CategoryInfo {
        val category = categoryRepository.findByUserIdAndName(userId, name)

        return CategoryInfo(
            id = category.id!!,
            name = category.name,
            icon = category.icon,
            color = category.color,
        )
    }
}
