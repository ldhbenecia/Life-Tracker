package com.benecia.lifetracker.domain.todo.dto

import com.benecia.lifetracker.todocore.model.info.CategoryInfo

data class CategoryResponse(
    val id: Long,
    val name: String,
    val icon: String,
    val color: String,
) {
    companion object {
        fun from(info: CategoryInfo): CategoryResponse {
            return CategoryResponse(
                id = info.id,
                name = info.name,
                icon = info.icon,
                color = info.color,
            )
        }
    }
}
