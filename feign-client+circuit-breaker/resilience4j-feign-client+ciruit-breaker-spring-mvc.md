# Resilience4j : Feign Client + CircuitBreaker (2.7.5) with Spring MVC



## 참고자료

- RateLimiter : https://resilience4j.readme.io/docs/ratelimiter

<br/>



## 예제 Github

Spring Boot 2.7.5 버전 기반으로 직접 작성한 코드는 [github.com/chagchagchag/try-it-log/mvc_feign_circuit_breaker_example](https://github.com/chagchagchag/try-it-log/tree/main/mvc_feign_circuit_breaker_example) 에 있습니다.<br/>

<br/>



## spring-cloud-starter-openfeign vs Resilience4j

이 문서는 2.7.5 버전을 기준으로 하고, SpringMVC 에서의 CircuitBreaker, Resilience4j 연동에 대해 정리합니다. 직접 처음부터 모두 세팅해보니 Spring Cloud 의 OpenFeign 만으로는 Resilience4j 의 공식문서에서 다루는 내용을 커버하기 쉽지 않았습니다.<br/>

그래서 제 생각은 가급적이면 FeignClient, CircuitBreaker 는 Resilience4j 의 기능을 단독으로 사용하시는 것을 추천드립니다. Spring Cloud 에서 제공하는 Gateway, Eureka Server, Discovery Client, Config Server 등은 모두 활용성도 놓고 좋은 라이브러리입니다. 다만 FeignClient, CircuitBreaker 는 Spring Cloud 에서의 지원이 조금은 부족하다는 판단이 들었습니다.<br/>

어쩌면 resilience4j 측에서 `spring-cloud-starter-*` 에 대한 지원을 제대로 못하고 있어서 일까 싶기도 합니다.<br/>



## 의존성

```groovy
plugins {
	id 'java'
	id 'org.springframework.boot' version '2.7.5'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
}

ext {
	springCloudVersion = "2021.0.3"
	resilience4jVersion = '1.7.0' // 이 버전을 잘 맞춰야 했습니다.
	openFeignVersion = '13.2.1'
}

dependencies {
	implementation "io.github.resilience4j:resilience4j-feign:${resilience4jVersion}"
	implementation "io.github.resilience4j:resilience4j-circuitbreaker:${resilience4jVersion}"
	implementation "io.github.resilience4j:resilience4j-ratelimiter:${resilience4jVersion}"

	implementation "org.springframework.cloud:spring-cloud-starter-openfeign"
	implementation "io.github.openfeign:feign-gson:${openFeignVersion}"
	implementation "io.github.openfeign:feign-core:${openFeignVersion}"
	implementation "io.github.openfeign:feign-jackson:${openFeignVersion}"
//	implementation 'org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j'
    
    // ...
}
```

<br/>



## RateLimiter

- 참고 : https://resilience4j.readme.io/docs/ratelimiter



Resilience4j Feign Client 에서 사용하는 RateLimiter 는 Spring Cloud Gateway 의 Rate Limiter 와는 조금 다른 개념입니다.<br/>

기본적으로는 여러 스레드들 각각이 Permission 을 획득했을 때에 그 스레드가 담당하고 있는 로직들을 처리할 수 있게 됩니다. API 호출 같은 경우 역시 너무 잦은 호출을 할 경우 여기에 대해서 너무 많은 요청을 보냈으니 잠시 뒤에 다시 보내라는 Response 를 보내는 것이 효율적인데 Feign Client 에 대해서 Rate Limit 기능을 제공하는 것이 Resilience4j Rate Limiter 입니다.

![](https://files.readme.io/44ca055-rate_limiter.png)



RateLimiter 에서는 각각 별개의 스레드가 Permission 을 얻으면 동작을 수행 가능합니다.<br/>

RateLimiter 의 구현체는 AtomicRateLimiter이고 AtomicRateLimiter 는 AtomicReference 를 이용해서 State 를 관리합니다. AtomicRateLimiter.State가 상태를 관리할 때 사용하는 클래스인데, AtomicRateLimiter.State 는 완전하게 immutable(불변)입니다. AtomicRateLimiter.State 는 아래와 같은 필드들을 가집니다.

- `activeCycle` - cycle number that was used by the last call
- `activePermissions` - count of available permissions after the last call.
- `nanosToWait` - count of nanoseconds to wait for permission for the last call

<br/>



### Resilience4j RateLimiter 설정

#### 네이밍 컨벤션

Resilience4j RateLimiter 는 다음과 같은 순서로 설정을 합니다.

- (1) RateLimiterConfig 객체 생성 : 이때 timeoutDuration, limitRefreshPeriod, limitForPeriod 를 커스텀하게 정의해서 RateLimiterConfig 객체를 생성합니다.
- (2) RateLimiterRegistry.of(RateLimiterConfig) : (1) 에서 만든 RateLimiterConfig 를 기반으로 RateLimiterRegistry 객체를 생성합니다.
- (3) RateLimiterRegistry.registry("RateLimiter명") : (2) 에서 만든 Registry 를 기반으로 RateLimiter 객체를 생성합니다.



요약해보면 이렇습니다. 

- (1) `RateLimiterConfig`  객체 생성
- (2) `RateLimiterRegistry` 객체 생성 (RateLimiterRegistry.of(config))
- (3) `RateLimiter` 객체 생성



Resilience4j 계열의 라이브러리들이 설정이 어려워보이지만 이런 네이밍 컨벤션이 정형화되어서 다른 기능(CircuitBreaker 등)을 설정할때도 비슷한 네이밍컨벤션을 가지고 있기에 이 규칙만 잘 알면 다른 기능 구현시에 문서를 보고 설정방식을 파악하는 것이 더 수월해지게 됩니다.

- `---Config` 객체 생성 → `---Registry` 객체 생성 → `---Registry` 객체로 RateLimiter, CircuitBreaker 등을 생성

<br/>



#### e.g.

```java
public class SomethingRateLimiterConfig {
    @Bean
  public RateLimiter jsonPlaceholderRateLimiter(){
    RateLimiterConfig config = RateLimiterConfig.custom()
        .timeoutDuration(Duration.ofMillis(15)) // thread 가 permission 을 얻기까지 기다릴 default 대기 시간(timeout 개념)
        .limitForPeriod(30) // period 하나당 허용할 permission 의 개수
        .limitRefreshPeriod(Duration.ofMillis(5)) // Period 의 크기. 이 Period 동안 limitForPeriod 만큼의 Permission 들이 채워진다.
        .build();

    RateLimiterRegistry registry = RateLimiterRegistry.of(config);

    return registry.rateLimiter("jsonPlaceholder");
  }
}
```

timeoutDuration

- default : 5 (s)
- thread 가 permission 을 얻기까지 기다릴 default 대기 시간(timeout 개념) 을 의미합니다.

limitForPeriod

- default : 500 (ns)
- period 하나당 허용할 permission 의 개수

limitRefreshPeriod

- default : 50 (개)
- Period 의 크기를 의미합니다. Period 에는 limitForPeriod 만큼의 여러 Permission 들이 허용될 수 있습니다. 

<br/>



## CircuitBreaker

참고

- [Reactive CircuitBreaker](https://chagchagchag.github.io/docs-spring-webflux/spring-cloud-reactive-circuitbreaker/reactive-circuit-breaker-basic/)

- [Service resiliency with Spring Boot and Resilience4j(opens in a new tab)](https://symphony.is/about-us/blog/service-resiliency-with-spring-boot-and-resilience4j)
- [Circuitbreaker를 사용한 장애 전파 방지](https://oliveyoung.tech/blog/2023-08-31/circuitbreaker-inventory-squad/)

<br/>



### CircuitBreaker 의 3가지 상태

#### Closed

![](https://chagchagchag.github.io/docs-spring-webflux/_next/static/media/CLOSED-STATUS.c93bf1c6.png)

- 정상적으로 요청을 받을 수 있는 상태입니다.
- 처음 보거나 오랜만에 봤다면 `Closed` 라는 단어를 보고 차단되었다고 착각하기 쉽습니다. 주의해야 합니다.
- `Closed` 는 회로차단기의 스위치를 닫아(Closed)서 스위치가 붙어있는 상태를 기억하면 이해가 쉽습니다.

<br/>

#### Half Open

![](https://chagchagchag.github.io/docs-spring-webflux/_next/static/media/HALF-OPEN-STATUS.390ba33a.png)

- 트래픽을 어느 정도 흘려본 후 Open 을 유지할지 Closed 로 변경할지 결정하는 상태입니다.

<br/>

#### Open

![](https://chagchagchag.github.io/docs-spring-webflux/_next/static/media/OPEN-STATUS.1d030594.png)

- Circuit Breaker 가 회로를 끊어둔 상태입니다.
- 회로 차단기가 켜져있는 상태입니다.
- 특정 목적지로 가는 트래픽을 차단하고 있는 상태입니다.

<br/>



Resilience4j 의 로고 역시 이와 같은 3종류의 상태를 잘 표현하고 있습니다. 검은색 원은 Closed, 반정도 차있는 원은 Half Open, 비어있는 원은 Open 상태를 의미합니다.

![](https://chagchagchag.github.io/docs-spring-webflux/_next/static/media/logo.c48dcf8d.png)

<br/>



### CircuitBreaker 의 상태 변이

Circuit Breaker 를 사용할 때 이해해야 하는 중요한 개념입니다.

![](https://files.readme.io/39cdd54-state_machine.jpg)

> *출처 :* [resilience4j.readme.io/docs/circuitbreaker#introduction](https://resilience4j.readme.io/docs/circuitbreaker#introduction)<br/>



Closed → Open

- Closed 에서는 Open 으로만 상태 변화가 가능합니다.
- 통신이 열려있는 상태에서는 차단하는 것만 가능하다는 것으로 이해하면 기억하기 쉽습니다.

<br/>



Half Open → Open, Half Open → Closed

- Half Open 상태에서는 Open, Closed 상태로만 변화가 가능합니다.

<br/>



Open → Half Open

- 회로가 차단되어 있는 Open 상태에서는 Half Open 상태로만 진입이 가능합니다.
- Half Open 으로 전환하는 방법은 코드로 실행시키는 방식, 일정시간 대기 후 Open 으로 전환되도록 하는 방식이 있습니다. 일반적으로 코드 레벨에서 하드코딩으로 Half Open 으로 전환되도록 하는 방식은 추천되는 방식은 아닙니다.
- 가급적이면 일정 시간 이후에 Half Open 이 되도록 delay 시간을 주어야 합니다. 여기에 관련된 설정값은 아래와 같습니다.
  - enableAutomaticTransitionFromOpenToHalfOpen()
  - waitDurationInOpenState()
- yml 에서는 아래의 속성들을 이용해서 활성화 가능합니다.
  - automatic-transition-from-open-to-half-open-enabled
    - open 에서 halfopen 으로 자동으로 전환하게끔 enable 하는 메서드입니다.
    - 기본값은 false 이지만 위에서는 enable 시켜서 true 가 되었습니다.
  - wait-duration-in-open-state
    - open 에서 half open 으로 전환할 때 까지 필요한 시간(Duration)입니다.
    - 설정하지 않으면 기본값으로 설정되며 기본값은 60초 입니다.

<br/>

### Sliding Window

![](https://chagchagchag.github.io/docs-spring-webflux/_next/static/media/SLIDING-WINDOW.30a395e3.png)



슬라이딩 윈도우는 위와 같은 방식으로 동작합니다. circular array 처럼 동작합니다.<br/>

슬라이딩 윈도우는 실패, 성공 여부를 sliding window size 만큼 쌓아두고 실패율(failure rate)을 측정하기 위해 사용합니다. circuit breaker 를 이용해서 통신을 차단하거나 허용할 때는 이 실패율을 기준으로 판단합니다.<br/>

sliding window 로 측정을 할 때에는 sliding Window Size 에 도달할 때 까지 계속해서 측정을 합니다. 그리고 측정한 개수가 n개에 도달했을 경우 가장 최초에 저장했던 결과를 제거한 후 새로운 결과를 저장합니다.<br/>

<br/>



#### Failure Rate

failure rate 는 실패횟수를 Sliding Window 사이즈로 나눈 수치를 Failure Rate 라고 합니다.

- failure rate = 실패한 호출 수 / Sliding Window size

![](https://chagchagchag.github.io/docs-spring-webflux/_next/static/media/FAILURE-RATE.d69704bf.png)

슬라이딩 윈도우 측정 시에 새로운 측정 값이 추가될 때 Sliding Window 전체의 실패 갯수를 측정하는 방식으로 측정하지 않습니다. O(n) 의 시간이 걸리기 때문입니다. 새로운 측정 값이 들어왔을 때 새로운 측정 결과는 더하고 이전 측정 결과는 뺀 후에 이것을 슬라이딩 윈도우 사이즈로 나누어서 새로운 `failure rate` 를 측정하게 됩니다.

<br/>



### Resilience4j circuit breaker 설정

#### 네이밍 컨벤션

CircuitBreaker 역시 RateLimiter 챕터에서 살펴봤듯 다음과 같은 네이밍 컨벤션을 따른다면, 공식문서를 파악할때 한결 이해가 수월해집니다.

`---Config` 객체 생성 → `---Registry` 객체 생성 → `---Registry` 객체로 RateLimiter, CircuitBreaker 등을 생성

- (1) `CircuitBreakerConfig`  객체 생성
- (2) `CircuitBreakerRegistry` 객체 생성 (CircuitBreakerRegistry.of(config))
- (3) `CircuitBreaker` 객체 생성

<br/>



#### e.g.

```java
package io.example.feign.mvc_feign_circuit_breaker_example.config.circuitbreaker;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonPlaceholderCircuitBreakerConfig {

  @Bean(name = {"jsonPlaceholderCircuitBreaker"})
  public CircuitBreaker jsonPlaceholderCircuitBreaker(){
    final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slowCallRateThreshold(50)
        .waitDurationInOpenState(Duration.ofMillis(1000))
        .slowCallDurationThreshold(Duration.ofSeconds(2))
        .permittedNumberOfCallsInHalfOpenState(3)
        .minimumNumberOfCalls(10)
        .slidingWindowType(SlidingWindowType.TIME_BASED)
        .slidingWindowSize(5)
//        .recordException(e -> INTERNAL_SERVER_ERROR
//            .equals(getResponse().getStatus()))
        .recordExceptions(IOException.class, TimeoutException.class)
//        .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
        .build();

    CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

    return registry.circuitBreaker("jsonplaceholder");
  }
}

```

`@Configuration` 으로 선언한 클래스 내에서 `@Bean` 으로 생성하고 이런 것이 뭔가 굉장히 어려워보일 수도 있을 수 있습니다.<br/>

CircuitBreaker 는 단순 자바객체로도 생성 가능한데 이것을 Bean 으로 등록하면 스프링 애플리케이션 컨텍스트에 등록해서 의존성 주입을 받아 사용할 수 있고, 객체의 라이프 사이클을 스프링에서 관리해주기 때문에 Bean 으로 등록했습니다.<br/>

<br/>

예를 들면 위의 코드가 귀찮다면 아래와 같이 직접 일반 자바 객체로 생성해서 사용하는 것 역시 가능합니다. 또는 안드로이드 등과 같은 자바기반 프레임워크에서도 아래와 같이 단순 자바 코드로 CircuitBreaker 객체를 생성하는 것이 가능합니다.

```java
final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
    .failureRateThreshold(50)
    .slowCallRateThreshold(50)
    .waitDurationInOpenState(Duration.ofMillis(1000))
    .slowCallDurationThreshold(Duration.ofSeconds(2))
    .permittedNumberOfCallsInHalfOpenState(3)
    .minimumNumberOfCalls(10)
    .slidingWindowType(SlidingWindowType.TIME_BASED)
    .slidingWindowSize(5)
//        .recordException(e -> INTERNAL_SERVER_ERROR
//            .equals(getResponse().getStatus()))
    .recordExceptions(IOException.class, TimeoutException.class)
//        .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
    .build();

CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

CircuitBreaker circuitBreaker = registry.circuitBreaker("jsonplaceholder");
```

<br/>



## Feign Client







