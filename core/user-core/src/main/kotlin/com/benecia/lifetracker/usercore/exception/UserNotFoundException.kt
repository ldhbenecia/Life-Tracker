package com.benecia.lifetracker.usercore.exception

import com.benecia.lifetracker.common.exception.CustomException

class UserNotFoundException(
    userId: String
) : CustomException(UserErrorCode.USER_NOT_FOUND)