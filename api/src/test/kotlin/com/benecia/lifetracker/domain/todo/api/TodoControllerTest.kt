package com.benecia.lifetracker.domain.todo.api

import com.benecia.lifetracker.domain.common.SpringSecurityCurrentUserProvider
import com.benecia.lifetracker.test.api.RestDocsTest
import com.benecia.lifetracker.test.api.RestDocsUtils.requestPreprocessor
import com.benecia.lifetracker.test.api.RestDocsUtils.responsePreprocessor
import com.benecia.lifetracker.todocore.model.command.TodoAddCommand
import com.benecia.lifetracker.todocore.model.info.TodoInfo
import com.benecia.lifetracker.todocore.service.TodoService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import java.time.LocalDateTime
import java.util.UUID

class TodoControllerTest : RestDocsTest() {
    private lateinit var todoService: TodoService
    private lateinit var currentUserProvider: SpringSecurityCurrentUserProvider
    private lateinit var controller: TodoController

    @BeforeEach
    fun setUp() {
        todoService = mockk()
        currentUserProvider = mockk()
        controller = TodoController(todoService, currentUserProvider)
        mockMvc = mockController(controller)
    }

    @Test
    fun readTodo() {
        val userId = UUID.randomUUID()
        val todoId = 1L

        val expected = TodoInfo(
            id = todoId,
            title = "서버 개발",
            category = "개발",
            scheduledDate = LocalDateTime.of(2025, 7, 8, 20, 0),
            notificationTime = LocalDateTime.of(2025, 7, 8, 18, 0),
            isDone = false,
        )

        every { currentUserProvider.getCurrentUserId() } returns userId
        every { todoService.readTodoById(todoId) } returns expected

        given()
            .contentType(ContentType.JSON)
            .get("/api/v1/todos/{id}", todoId)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "readTodo",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        parameterWithName("id").description("Todo ID"),
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("할 일 ID"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("할 일 제목"),
                        fieldWithPath("data.category").type(JsonFieldType.STRING).description("할 일 분류"),
                        fieldWithPath("data.scheduledDate").type(JsonFieldType.STRING).description("예약 날짜 및 시간 (ISO-8601)"),
                        fieldWithPath("data.notificationTime").type(JsonFieldType.STRING).description("알림 시간 (ISO-8601)"),
                        fieldWithPath("data.isDone").type(JsonFieldType.BOOLEAN).description("완료 여부"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 생성 시간")
                    ),
                ),
            )
    }

    @Test
    fun addTodo() {
        val commandSlot = slot<TodoAddCommand>()
        val userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

        every { currentUserProvider.getCurrentUserId() } returns userId
        every { todoService.addTodo(capture(commandSlot)) } returns 1L

        val requestBody = mapOf(
            "title" to "서버 개발",
            "category" to "개발",
            "scheduledDate" to "2025-07-08T20:00:00",
            "notificationTime" to "2025-07-08T18:00:00",
        )

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/api/v1/todos")
            .then()
            .status(HttpStatus.CREATED)
            .apply(
                document(
                    "addTodo",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("Todo 제목"),
                        fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                        fieldWithPath("scheduledDate").type(JsonFieldType.STRING).description("예약된 날짜 및 시간 (ISO8601)"),
                        fieldWithPath("notificationTime").type(JsonFieldType.STRING)
                            .description("알림 시간 (ISO8601), null 가능").optional(),
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 할 일 ID"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 생성 시간")
                    ),
                ),
            )
    }
}
