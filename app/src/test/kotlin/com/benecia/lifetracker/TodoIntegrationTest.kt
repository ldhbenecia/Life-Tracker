package com.benecia.lifetracker

import com.benecia.lifetracker.common.response.ApiResponse
import com.benecia.lifetracker.todoapi.dto.TodoRequest
import com.benecia.lifetracker.userapi.dto.UserRequest
import com.benecia.lifetracker.userapi.dto.UserResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoIntegrationTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {
    @Test
    fun `회원가입 후 Todo 등록 및 조회`() {
        val uniqueUsername = "test_" + UUID.randomUUID().toString().take(8)
        val userRequest = UserRequest(username = uniqueUsername, email = "$uniqueUsername@test.com")

        val userResponse = mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(userRequest)
        }.andExpect {
            status { isCreated() }
        }.andReturn().response.contentAsString.let {
            val typeRef = object : com.fasterxml.jackson.core.type.TypeReference<ApiResponse<UserResponse>>() {}
            objectMapper.readValue(it, typeRef).data!!
        }

        val todoRequest = TodoRequest(
            userId = userResponse.id,
            title = "test data title",
            description = "test data description",
            scheduledTime = LocalDateTime.now().plusDays(1),
            isDone = false
        )

        mockMvc.post("/api/v1/todos") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(todoRequest)
        }.andExpect {
            status { isCreated() }

            mockMvc.get("/api/v1/todos/user/${userResponse.id}")
                .andExpect {
                    status { isOk() }
                    jsonPath("$.data[0].title") { value("test data title") }
                }
        }
    }
}