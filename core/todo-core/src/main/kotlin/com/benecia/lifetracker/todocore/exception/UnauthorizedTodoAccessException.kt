package com.benecia.lifetracker.todocore.exception

import com.benecia.lifetracker.common.exception.CustomException

class UnauthorizedTodoAccessException(
    val todoId: Long,
    val currentUserId: String,
) : CustomException(TodoErrorCode.UNAUTHORIZED_TODO_ACCESS)