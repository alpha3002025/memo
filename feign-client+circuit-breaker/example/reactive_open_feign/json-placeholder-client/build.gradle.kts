plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "io.chagchagchag.youtube"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.2"

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

	 // MVC
	 // open feign
//	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
//	// feign client (http)
//	implementation("io.github.openfeign:feign-okhttp:13.1")
	implementation("io.github.openfeign:feign-httpclient:13.1")
//	implementation("org.springframework.cloud:spring-cloud-starter-gateway")

	// Reactive Feign
//	implementation("io.github.openfeign:feign-core:13.2.1")
	implementation("io.github.openfeign:feign-reactive-wrappers:13.2.1")
	implementation("io.github.openfeign:feign-jackson:13.2.1")
	implementation("io.github.openfeign:feign-gson:13.2.1")
//	testImplementation("io.github.openfeign:feign-okhttp:13.1")
//	testImplementation("io.github.openfeign:feign-jaxrs:13.1")

	// mockwebserver
	testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
