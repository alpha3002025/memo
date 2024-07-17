## Feign, CircuitBreaker, RateLimiter, Bulkhead, Retry

Feign 은 주로 REST API 호출, MSA 간 REST API 통신 시에 사용되는 REST API Client 라이브러리입니다.

- REST API 호출
- MSA 간 REST API 통신

<br/>



Feign 라이브러리는 spring cloud open feign 이 있고, Resilience4j 에서 제공하는 open feign 도 있습니다.

- spring cloud open feign 
- resilience4j open feign 

<br/>



둘중 어느 것이 더 좋다고 할수는 없지만 spring cloud open feign 의 경우 spring 과의 통합성이 좋다고 할 수 있습니다. resilience4j open feign 의 경우 라이브러리가 안정적이며, 스프링에 종속되지 않은 개발이 가능하다는 장점이 있습니다.<br/>

제 경우에는 상용 서비스의 백엔드 개발 시에 resilience4j open feign 을 팀 내에서 채택해서 사용했습니다. 이유는 안정적인 라이브러리여서 입니다. spring cloud open feign 는 spring 과의 통합성이 좋기는 하지만, resilience4j open feign 을 통해 만든 일반 자바 객체를 Spring 의 스타일에 맞게끔 잘 활용하면 되기에 안정성이 중요하다면 굳이 spring cloud open feign 을 고집해서 사용할 필요까지는 없을 것 같습니다.<br/>



이 외에도 Netflix 에서 제공하는 Feign Client 도 있습니다. Netflix Feign Client 는 현재 개발이 종료되었으며 유지보수단계입니다.<br/>

<br/>



## Feign 과 함께 사용할 수 있는 기능들

FeignDecorators 내에는 CircuitBreaker, RateLimiter, Retry, BulkHead 등과 같은 기능을 바인딩할 수 있습니다.

Circuit Breaker 
- Circuit Breaker 는 특정 API의 health check 를 해서 통신이 가능한지 아닌지 체크하고 통신이 불가능할 경우 이 장애가 다른 서비스에 전파되지 않도록 회로 차단기를 켜서 해당 API 로의 통신을 중단할 수 있습니다. 
- 또한 이렇게 장애가 생긴 해당 API가 회복될때까지 주기적으로 체크하고, 특정 횟수나 비율만큼 성공하면 HALF\_OPEN → CLOSED 로 전환하는 방식으로 장애 복구 시점에 수동 배포 없이 대응이 가능하다는 점이 있습니다. `Self-Healing` 과 같은 개념입니다.

Rate Limiter
- Rate Limiter 를 연동해서 특정 타임 시퀀스 안에 몇개의 요청까지만 허용할지, 요청 하나당 응답 지연시간은 어느 정도(ns, ms, s)를 허용할지 역시 설정할 수 있습니다.

Retry
- 특정 사용자 정의 Exception 에 대해서 Retry를 어떻게 할지 등을 지정하는 기능입니다. 
- Retry 는 최대 몇번을 재시도할지, Timeout 은 어느 정도로 지정할지 등을 지정할 수 있습니다.

<br/>



