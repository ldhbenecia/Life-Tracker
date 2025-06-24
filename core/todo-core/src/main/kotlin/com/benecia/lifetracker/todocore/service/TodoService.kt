package com.benecia.lifetracker.todocore.service

import org.springframework.stereotype.Service

@Service
class TodoService(
    // private val todoRepository: TodoRepository // 실제로는 storage 모듈의 Repository를 주입
) {

//    @Transactional(readOnly = true)
//    fun findById(id: Long): TodoInfo {
//        // 1. Repository를 통해 Entity 조회
//        // val todo = todoRepository.findById(id)
//        //     .orElseThrow {
//        //         // 2. 결과가 없다면, 직접 만든 명확한 이름의 예외를 던집니다.
//        //         TodoNotFoundException(todoId = id)
//        //     }
//
//        // 3. 결과를 반환합니다.
//        // return todo.toInfo()
//
//        // --- 임시 로직 ---
//        if (id > 100) throw TodoNotFoundException(todoId = id)
//        return TodoInfo(id, "임시 제목", "내용")
//    }
//
//    @Transactional
//    fun updateTitle(command: TodoUpdateCommand): TodoInfo {
//        // 1. 수정할 Todo가 존재하는지 먼저 확인합니다.
//        // val todo = todoRepository.findById(command.todoId)
//        //     .orElseThrow { TodoNotFoundException(todoId = command.todoId) }
//
//        // 2. [핵심] 비즈니스 로직: 권한을 검사합니다.
//        // if (todo.userId != command.currentUserId) {
//        //     // 소유자가 아니라면, 권한 없음 예외를 던집니다.
//        //     throw UnauthorizedTodoAccessException(
//        //         todoId = command.todoId,
//        //         currentUserId = command.currentUserId
//        //     )
//        // }
//
//        // 3. 모든 검사를 통과하면, 실제 비즈니스 로직을 수행합니다.
//        // todo.update(command.title)
//        // return todo.toInfo()
//
//        // --- 임시 로직 ---
//        if (command.todoId > 100) throw TodoNotFoundException(todoId = command.todoId)
//        if (command.todoId == 1L && command.currentUserId != 1L) {
//            throw UnauthorizedTodoAccessException(command.todoId, command.currentUserId)
//        }
//        return TodoInfo(command.todoId, command.title ?: "수정된 제목", "내용")
//    }
}