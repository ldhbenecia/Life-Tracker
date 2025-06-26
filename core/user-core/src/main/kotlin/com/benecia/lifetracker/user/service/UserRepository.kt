package com.benecia.lifetracker.user.service

interface UserRepository {
    fun add(name: String): Long
    fun read(id: Long): User?
}