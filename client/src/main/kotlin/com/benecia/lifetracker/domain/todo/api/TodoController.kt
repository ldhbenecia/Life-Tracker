package com.benecia.lifetracker.domain.todo.api

import com.benecia.lifetracker.todocore.service.TodoService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/todos")
class TodoController(
    private val todoService: TodoService
) {

//    @PostMapping
//    fun createTodo(
//        @Valid @RequestBody request: TodoCreateRequest
//    ): ResponseEntity<ApiResponse<TodoInfo>> {
//        // Service에 create 명령을 내리고, 성공 결과를 TodoInfo로 받습니다.
//        val todoInfo = todoService.create(request.toCommand()) // request를 command로 변환
//
//        // 성공 결과를 ApiResponse.created() 로 감싸서 반환합니다.
//        return ResponseEntity
//            .status(HttpStatus.CREATED)
//            .body(ApiResponse.created(todoInfo))
//    }
//
//    @GetMapping("/{id}")
//    fun getTodoById(@PathVariable id: Long): ResponseEntity<ApiResponse<TodoInfo>> {
//        // Service에 findById 명령을 내립니다.
//        // Service 내부에서 todo가 없으면 TodoNotFoundException이 발생하고,
//        // 이 예외는 GlobalExceptionHandler가 자동으로 처리합니다.
//        val todoInfo = todoService.findById(id)
//
//        // 예외가 발생하지 않고 성공했다면, 결과를 ApiResponse.success() 로 감싸서 반환합니다.
//        return ResponseEntity.ok(ApiResponse.success(todoInfo))
//    }
}