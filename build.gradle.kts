import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.12"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.sonarqube") version "4.4.1.3373"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.4.32"
}

var group = "com.uahannam"
var version = "0.0.1-SNAPSHOT"
var springCloudVersion = "2022.0.4"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

/* Spring Cloud 의존성 버전 최신화*/
configure<DependencyManagementExtension> {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
	}
}


sonar {
	properties {
		property("sonar.projectKey", "ua-hannam_seller-service")
		property("sonar.organization", "msa2024")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
