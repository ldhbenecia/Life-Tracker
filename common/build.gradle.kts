plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

val springBootVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
}
