package com.benecia.lifetracker.common.exception

interface ErrorCode {
    val code: Int
    val name: String
    val message: String
}
