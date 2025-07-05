package com.benecia.lifetracker.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.JdkClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.net.http.HttpClient
import java.time.Duration

@Configuration
class RestClientConfig {

    @Bean
    fun restClient(): RestClient {
        val httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build()

        val requestFactory = JdkClientHttpRequestFactory(httpClient)

        return RestClient.builder()
            .requestFactory(requestFactory)
            .build()
    }
}