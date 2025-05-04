package com.benecia.lifetracker.infra.db

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class TodoDatabaseInitializer {

    private val logger = LoggerFactory.getLogger(TodoDatabaseInitializer::class.java)

    @Bean
    fun initTodoDatabase(jdbcTemplate: JdbcTemplate): CommandLineRunner {
        return CommandLineRunner {
            logger.info("Checking if todos table exists...")

            val tableExists = DatabaseUtils.tableExists(jdbcTemplate, "todos")

            if (!tableExists) {
                logger.info("Creating todos table...")

                try {
                    jdbcTemplate.execute(
                        """
                        CREATE TABLE todos(
                            id UUID PRIMARY KEY,
                            user_id UUID NOT NULL,
                            title VARCHAR(255) NOT NULL,
                            description TEXT,
                            scheduled_time TIMESTAMP NOT NULL,
                            is_done BOOLEAN NOT NULL DEFAULT FALSE,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL
                        );  
                        """.trimIndent())

                    logger.info("Todos table created successfully!")
                } catch (e: Exception) {
                    logger.error("Error while creating todos", e)
                    throw e
                }
            } else {
                logger.info("Todos table already exists, skipping creation")
            }
        }
    }
}