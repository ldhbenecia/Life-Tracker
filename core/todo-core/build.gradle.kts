plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":core:user-core"))
    compileOnly("org.springframework:spring-context")
}
