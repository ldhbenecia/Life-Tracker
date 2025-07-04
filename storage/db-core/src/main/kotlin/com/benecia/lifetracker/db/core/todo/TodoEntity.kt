package com.benecia.lifetracker.db.core.todo

import com.benecia.lifetracker.db.core.BaseEntity
import com.benecia.lifetracker.todocore.service.Todo
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "todo")
class TodoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: UUID,

    @Column(nullable = false)
    var title: String,

    var category: String,

    @Column(nullable = false)
    var scheduledDate: LocalDateTime,

    var notificationTime: LocalDateTime?,

    @Column(nullable = false)
    var isDone: Boolean,

) : BaseEntity() {

    companion object {
        fun from(todo: Todo): TodoEntity {
            return TodoEntity(
                userId = todo.userId,
                title = todo.title,
                category = todo.category,
                scheduledDate = todo.scheduledDate,
                notificationTime = todo.notificationTime,
                isDone = todo.isDone
            )
        }
    }

    fun toDomain(): Todo = Todo(
        id = this.id,
        userId = this.userId,
        title = this.title,
        category = this.category,
        scheduledDate = this.scheduledDate,
        notificationTime = this.notificationTime,
        isDone = this.isDone
    )

    fun modifyWith(todo: Todo) {
        this.title = todo.title
        this.category = todo.category
        this.scheduledDate = todo.scheduledDate
        this.notificationTime = todo.notificationTime
        this.isDone = todo.isDone
    }
}