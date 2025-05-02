plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":core:user-core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}
