package com.benecia.lifetracker.infra.db

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class UserDatabaseInitializer {

    private val logger = LoggerFactory.getLogger(UserDatabaseInitializer::class.java)

    @Bean
    fun initUserDatabase(jdbcTemplate: JdbcTemplate): CommandLineRunner {
        return CommandLineRunner {
            logger.info("Checking if users table exists...")

            val tableExists = DatabaseUtils.tableExists(jdbcTemplate, "users")
            if (!tableExists) {
                logger.info("Creating users table...")

                try {
                    jdbcTemplate.execute(
                        """
                        CREATE TABLE users (
                            id UUID PRIMARY KEY,
                            username VARCHAR(100) NOT NULL,
                            email VARCHAR(100) NOT NULL,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL,
                            deleted_at TIMESTAMP
                        );
                    """.trimIndent()
                    )

                    logger.info("Users created successfully")
                } catch (e: Exception) {
                    logger.error("Error while creating users", e)
                    throw e
                }
            } else {
                logger.info("Users table already exists, skipping creation")
            }
        }
    }
}