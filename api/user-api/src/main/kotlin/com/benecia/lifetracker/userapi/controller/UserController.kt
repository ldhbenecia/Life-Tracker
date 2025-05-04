package com.benecia.lifetracker.userapi.controller

import com.benecia.lifetracker.common.response.ApiResponse
import com.benecia.lifetracker.userapi.dto.UserRequest
import com.benecia.lifetracker.userapi.dto.UserResponse
import com.benecia.lifetracker.usercore.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<ApiResponse<UserResponse>> {
        val user = userRequest.toEntity()
        val savedUser = userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse(201, "User created", data = UserResponse.fromEntity(savedUser)))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<ApiResponse<UserResponse>> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(ApiResponse(200, data = UserResponse.fromEntity(user)))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<ApiResponse<List<UserResponse>>> {
        val users = userService.getAllUsers().map { UserResponse.fromEntity(it) }
        return ResponseEntity.ok(ApiResponse(200, data = users))
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody request: UserRequest
    ): ResponseEntity<ApiResponse<UserResponse>> {
        val user = request.toEntity().withId(id)
        val updated = userService.updateUser(user)
        return ResponseEntity.ok(ApiResponse(200, "User updated", UserResponse.fromEntity(updated)))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<ApiResponse<Void>> {
        userService.softDeleteUserById(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse(204, "User deleted"))
    }
}