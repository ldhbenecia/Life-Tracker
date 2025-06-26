plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":common"))
    compileOnly("org.springframework:spring-context")
}
