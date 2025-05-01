package com.benecia.lifetracker.user.controller

import com.benecia.lifetracker.common.exception.CustomException
import com.benecia.lifetracker.common.exception.ErrorCode
import com.benecia.lifetracker.user.dto.UserRequest
import com.benecia.lifetracker.user.dto.UserResponse
import com.benecia.lifetracker.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val response = userService.createUser(userRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        return try {
            val response = userService.getUserById(id)
            ResponseEntity.ok(response)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val responses = userService.getAllUsers()
        return ResponseEntity.ok(responses)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody request: UserRequest
    ): ResponseEntity<UserResponse> {
        return try {
            val response = userService.updateUser(id, request)
            ResponseEntity.ok(response)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.softDeleteUserById(id)
        return ResponseEntity.noContent().build()
    }
}