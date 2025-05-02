package com.benecia.lifetracker.common.util

import org.springframework.jdbc.core.JdbcTemplate

class DatabaseUtils {
    companion object {
        fun tableExists(jdbcTemplate: JdbcTemplate, tableName: String): Boolean {
            val sql = """
                SELECT EXISTS (
                    SELECT FROM information_schema.tables
                    WHERE table_schema = 'public'
                    AND table_name = ?
                );
                """.trimIndent()

            return try {
                jdbcTemplate.queryForObject(sql, Boolean::class.java, tableName) ?: false
            } catch (e: Exception) {
                false
            }
        }
    }
}