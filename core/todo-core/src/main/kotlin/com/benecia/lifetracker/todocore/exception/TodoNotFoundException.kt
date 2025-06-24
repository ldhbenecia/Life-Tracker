package com.benecia.lifetracker.todocore.exception

import com.benecia.lifetracker.common.exception.CustomException

class TodoNotFoundException(
    val todoId: Long
) : CustomException(TodoErrorCode.TODO_NOT_FOUND)