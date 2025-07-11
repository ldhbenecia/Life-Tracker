package com.benecia.lifetracker.db.core.category

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoryJpaRepository : JpaRepository<CategoryEntity, Long> {
    fun findByUserIdAndId(userId: UUID, id: Long): CategoryEntity?
    fun findByUserIdAndName(userId: UUID, name: String): CategoryEntity?
}
