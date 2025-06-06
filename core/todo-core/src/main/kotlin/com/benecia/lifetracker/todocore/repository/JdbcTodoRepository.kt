package com.benecia.lifetracker.todocore.repository

import com.benecia.lifetracker.todocore.domain.Todo
import com.benecia.lifetracker.todocore.model.DailyTodoStats
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Repository
class JdbcTodoRepository(private val jdbcTemplate: JdbcTemplate): TodoRepository {

    private val logger = LoggerFactory.getLogger(JdbcTodoRepository::class.java)

    private val todoRowMapper = RowMapper { rs: ResultSet, _: Int ->
        Todo(
            id = UUID.fromString(rs.getString("id")),
            userId = UUID.fromString(rs.getString("user_id")),
            title = rs.getString("title"),
            description = rs.getString("description"),
            scheduledTime = rs.getTimestamp("scheduled_time").toLocalDateTime(),
            isDone = rs.getBoolean("is_done"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
            updatedAt = rs.getTimestamp("updated_at").toLocalDateTime(),
        )
    }

    override fun save(todo: Todo): Todo {
        if (todo.id == null) {
            todo.id = UUID.randomUUID()
        }

        val sql = """
            INSERT INTO todos (id, user_id, title, description, scheduled_time, is_done, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            todo.id,
            todo.userId,
            todo.title,
            todo.description,
            Timestamp.valueOf(todo.scheduledTime),
            todo.isDone,
            Timestamp.valueOf(todo.createdAt),
            Timestamp.valueOf(todo.updatedAt),
        )

        return todo;
    }

    override fun findById(id: UUID): Todo? {
        val sql = "SELECT * FROM todos WHERE id = ?"

        return try {
            jdbcTemplate.queryForObject(sql, todoRowMapper, id)
        } catch (e: Exception) {
            logger.warn("Todo with id $id not found", e)
            null
        }
    }

    override fun findAll(): List<Todo> {
        val sql = "SELECT * FROM todos ORDER BY scheduled_time"
        return jdbcTemplate.query(sql, todoRowMapper)
    }

    override fun findAllByUserId(userId: UUID): List<Todo> {
        val sql = "SELECT * FROM todos WHERE user_id = ? ORDER BY scheduled_time"
        return jdbcTemplate.query(sql, todoRowMapper, userId)
    }

    override fun update(todo: Todo): Todo {
        todo.updatedAt = LocalDateTime.now()

        val sql = """
            UPDATE todos
            SET title = ?, description = ?, scheduled_time = ?, is_done = ?, updated_at = ?
            WHERE id = ?
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            todo.title,
            todo.description,
            Timestamp.valueOf(todo.scheduledTime),
            todo.isDone,
            Timestamp.valueOf(todo.updatedAt),
            todo.id
        )

        return todo
    }

    override fun toggleDone(id: UUID): Todo? {
        val todo = findById(id) ?: return null

        val newDoneStatus = !todo.isDone
        val now = LocalDateTime.now()

        val sql = """
            UPDATE todos 
            SET is_done = ?, updated_at = ?
            WHERE id = ?
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            newDoneStatus,
            Timestamp.valueOf(now),
            id
        )

        todo.isDone = newDoneStatus
        todo.updatedAt = now

        return todo
    }

    override fun deleteById(id: UUID): Boolean {
        val sql = "DELETE FROM todos WHERE id = ?"
        val rowsAffected = jdbcTemplate.update(sql, id)
        return rowsAffected > 0
    }

    override fun countAllByUserId(userId: UUID): Int {
        val sql = "SELECT COUNT(*) FROM todos WHERE user_id = ?"
        return jdbcTemplate.queryForObject(sql, Int::class.java, userId) ?: 0
    }

    override fun countDoneByUserId(userId: UUID): Int {
        val sql = "SELECT COUNT(*) FROM todos WHERE user_id = ? AND is_done = true"
        return jdbcTemplate.queryForObject(sql, Int::class.java, userId) ?: 0
    }

    override fun findDoneCountGroupedByDate(userId: UUID): List<DailyTodoStats> {
        val sql = """
        SELECT DATE(scheduled_time) as day, COUNT(*) as done_count
        FROM todos
        WHERE user_id = ? AND is_done = true AND scheduled_time >= NOW() - INTERVAL '7 days'
        GROUP BY day
        ORDER BY day
    """.trimIndent()

        return jdbcTemplate.query(sql, { rs, _ ->
            DailyTodoStats(
                date = rs.getDate("day").toLocalDate(),
                doneCount = rs.getInt("done_count")
            )
        }, userId)
    }

    override fun findTodosToRemind(now: LocalDateTime): List<Todo> {
        val sql = """
            SELECT * FROM todos
            WHERE scheduled_time <= ? AND is_done = false
            ORDER BY scheduled_time
        """.trimIndent()

        return jdbcTemplate.query(sql, todoRowMapper, Timestamp.valueOf(now))
    }
}