package com.benecia.lifetracker.userapi.controller

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
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userRequest.toEntity()
        val savedUser = userService.createUser(user)
        val response = UserResponse.fromEntity(savedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromEntity(savedUser))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(UserResponse.fromEntity(user))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users.map { UserResponse.fromEntity(it) })
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody request: UserRequest
    ): ResponseEntity<UserResponse> {
        val user = request.toEntity().withId(id)
        val updated = userService.updateUser(user)
        return ResponseEntity.ok(UserResponse.fromEntity(updated))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.softDeleteUserById(id)
        return ResponseEntity.noContent().build()
    }
}