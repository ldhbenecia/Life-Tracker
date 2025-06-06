plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":infra"))
    implementation(project(":core:user-core"))
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
}
