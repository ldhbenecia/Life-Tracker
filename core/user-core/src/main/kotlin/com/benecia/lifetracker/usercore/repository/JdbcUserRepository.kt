package com.benecia.lifetracker.usercore.repository

import com.benecia.lifetracker.usercore.domain.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Repository
class JdbcUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {

    private val userRowMapper = RowMapper<User> { rs: ResultSet, _: Int ->
        User(
            id = UUID.fromString(rs.getString("id")),
            username = rs.getString("username"),
            email = rs.getString("email"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
            updatedAt = rs.getTimestamp("updated_at").toLocalDateTime(),
            deletedAt = rs.getTimestamp("deleted_at")?.toLocalDateTime()
        )
    }

    override fun save(user: User): User {
        if (user.id == null) {
            user.id = UUID.randomUUID()
        }

        val sql = """
                INSERT INTO users (id, username, email, created_at, updated_at, deleted_at)
                VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent()

        jdbcTemplate.update(
            sql,
            user.id,
            user.username,
            user.email,
            Timestamp.valueOf(user.createdAt),
            Timestamp.valueOf(user.updatedAt),
            user.deletedAt?.let { Timestamp.valueOf(it) }
        )

        return user;
    }

    override fun findById(id: UUID): User? {
        val sql = "SELECT * FROM users WHERE id = ? AND deleted_at IS NULL"

        return try {
            jdbcTemplate.queryForObject(
                sql,
                userRowMapper,
                id
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun findByUsername(username: String): User? {
        val sql = "SELECT * FROM users WHERE username = ? AND deleted_at IS NULL"

        return try {
            jdbcTemplate.queryForObject(
                sql,
                userRowMapper,
                username
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun findByAll(): List<User> {
        val sql = "SELECT * FROM users"
        return jdbcTemplate.query(sql, userRowMapper)
    }

    override fun update(user: User): User {
        user.updatedAt = LocalDateTime.now()

        val sql = """
            UPDATE users SET username = ?, email = ?, updated_at = ? 
            WHERE id = ?
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            user.username,
            user.email,
            Timestamp.valueOf(user.updatedAt),
            user.id
        )

        return user
    }

    override fun softDeleteById(id: UUID) {
        val now = LocalDateTime.now()

        val sql = """
            UPDATE users 
            SET deleted_at = ?, updated_at = ?
            WHERE id = ? AND deleted_at IS NULL
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            Timestamp.valueOf(now),
            Timestamp.valueOf(now),
            id
        )
    }
}