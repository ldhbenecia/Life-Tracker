plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":common"))

    implementation(project(":core:user-core"))
    implementation(project(":core:todo-core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
