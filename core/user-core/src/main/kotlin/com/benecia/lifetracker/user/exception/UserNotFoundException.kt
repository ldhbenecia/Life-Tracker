package com.benecia.lifetracker.user.exception

import com.benecia.lifetracker.common.exception.CustomException

class UserNotFoundException(
    userId: String
) : CustomException(UserErrorCode.USER_NOT_FOUND)