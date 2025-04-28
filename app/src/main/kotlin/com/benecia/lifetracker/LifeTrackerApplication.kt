package com.benecia.lifetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LifeTrackerApplication

fun main(args: Array<String>) {
	runApplication<LifeTrackerApplication>(*args)
}
