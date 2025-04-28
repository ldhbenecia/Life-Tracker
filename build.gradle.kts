plugins {
	kotlin("jvm")
	kotlin("plugin.spring") apply false
	id("org.springframework.boot") apply false
	id("io.spring.dependency-management")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

val projectGroup: String by project
val applicationVersion: String by project

allprojects {
	group = projectGroup
	version = applicationVersion

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "io.spring.dependency-management")

	the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
		imports {
			mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
		}
	}

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
