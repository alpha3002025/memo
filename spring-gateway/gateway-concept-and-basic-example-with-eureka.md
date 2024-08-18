## Spring Cloud Gateway Overview

아직 문서 작업 중 이에요!! 빠르게 정리하겠습니다!!!!!!!!!!!!!!!!!!!!!!!<br/>



## 참고

- [Spring Cloud Gateway Docs](https://cloud.spring.io/spring-cloud-gateway/reference/html/)

- https://cheese10yun.github.io/spring-cloud-gateway/

<br/>



## github

- https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-msa-services
- https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-non-eureka-msa-services

<br/>



## Route, Predicate, Filters

Route

- 특정 URI 에 대해서 어떤 조건(Predicate)일 때 어떤 Filter 를 적용할 지에 대한 URL을 매핑하는 규칙의 단위입니다. 이 규칙의 단위를 하나의 Route 객체로 표현합니다. 
- 주로 application.yml 내의 `spring.cloud.routes[i]` 에 하나씩 정의합니다.
- 각각의 Route 는 고유한 id 를 이용해서 각각의 Route 들을 구분할 수 있습니다. `spring.cloud.routes[i].id={라우트ID}` 로 정의합니다.
- Route 는 uri, predicate, filter 로 구분합니다.

<br/>



Predicate

- 어떤 요청에 대해 헤더, URI 패턴 등에 대해 조건값이 맞는지를 판단하는 조건식 역할을 하는 객체입니다.
- 주로 application.ym 내의 `spring.cloud.routes[i].predicate` 에 정의합니다.
- application.yml 에서의 자세한 사용 예는 글의 후반부 예제들을 확인해주시기 바랍니다.

<br/>



Filter

- HTTP  Request, Response 에 대한 Filter 역할을 수행합니다. Servlet 의 Filter 라기 보다는 조금 더 앞단에서의 동작하는 Gateway 서버가 인식할수 있는 Filter 의 개념으로 볼 수 있습니다.
- Route 내에 filter 를 두어서 특정 Route 에 대해 특정 Filter 가 동작되도록 지정할 수 있습니다.
- RewritePath 등을 지정할 때 Filter 를 사용합니다.
- 주로 application.yml 내의 `spring.cloud.routes[i].filters` 에 정의합니다.

<br/>



## 예제 github

- [Eureka 없이 구성한 Gateway + MSA](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-non-eureka-msa-services)
- [Eureka + Gateway + MSA](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-msa-services)
- [Eureka + Gateway + Websocket WAS (Servlet 버전)](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-websocket)

<br/>



## 예제 구성

이번 예제에서는 아래와 같이 MSA 를 구성합니다.

- service-discovery
- api-gateway
- order-api
- payment-api

<br/>



**실행**<br/>

아래의 순서로 실행시켜주시면 됩니다.

- service-discovery 실행 
- api-gateway 실행
- order-api, payment-api 실행 (두 WAS 실행 순서는 무관)

<br/>



## service-discovery

### build.gradle.kts

```kotlin
plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "io.chagchagchag.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
```

<br/>



### ServiceDiscoveryApplication

```java
package io.chagchagchag.example.service_discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryApplication.class, args);
	}

}
```

<br/>



### application.yml

```yaml
spring:
  application:
    name: service-discovery

server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    region: default
```

<br/>



### 구동

intellij 에서 구동시키고 나서 브라우저에서 http://localhost:8761/eureka 로 접속해봅니다. 

![](./img/gateway-concept-and-basic-example-with-eureka/1.png)

<br/>



## api-gateway

### build.gradle.kts

```kotlin
plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "io.chagchagchag.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
```

<br/>



### G

