dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.restdocs:spring-restdocs-mockmvc")
    api("org.springframework.restdocs:spring-restdocs-restassured")
    api("io.rest-assured:spring-mock-mvc")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(project(":core:user-core"))
    implementation(project(":common"))
}
