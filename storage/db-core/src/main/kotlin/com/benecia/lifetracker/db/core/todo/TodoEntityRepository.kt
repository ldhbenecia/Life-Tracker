package com.benecia.lifetracker.db.core.todo

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.todocore.exception.TodoErrorCode
import com.benecia.lifetracker.todocore.service.Todo
import com.benecia.lifetracker.todocore.service.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class TodoEntityRepository(
    private val todoJpaRepository: TodoJpaRepository
) : TodoRepository {

    override fun add(todo: Todo): Long {
        val entity = TodoEntity.from(todo)
        return todoJpaRepository.save(entity).id!!
    }

    override fun findById(id: Long): Todo {
        val entity = todoJpaRepository.findByIdOrNull(id)
            ?: throw CustomException(TodoErrorCode.TODO_NOT_FOUND)
        return entity.toDomain()
    }

    @Transactional
    override fun modify(id: Long, todo: Todo) {
        val entity = todoJpaRepository.findByIdOrNull(id)
            ?: throw CustomException(TodoErrorCode.TODO_NOT_FOUND)
        entity.modifyWith(todo)
    }
}