plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":core:todo-core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}
