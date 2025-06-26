package com.benecia.lifetracker.user.exception

import com.benecia.lifetracker.common.exception.CustomException

class UnauthorizedUserException(
    val accessorId: Long,
    val resourceId: String
) : CustomException(UserErrorCode.FORBIDDEN_USER_ACCESS)