plugins {
	kotlin("jvm") version "1.9.25" apply false
	kotlin("plugin.spring") version "1.9.25" apply false
	id("org.springframework.boot") version "3.4.5" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("java")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

allprojects {
	group = "com.benecia"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

// app 모듈에만 spring-boot 플러그인 적용
project(":app") {
	apply(plugin = "org.springframework.boot")
}
