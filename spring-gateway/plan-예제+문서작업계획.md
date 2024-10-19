# 예제 List

## Router 예제 

- [단순 게이트웨이 router](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/1-simple-gateway-router)
- [rewrite path filter 를 적용한 router](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/2-rewrite-path-router)
- [security 를 간단하게 적용한 router](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/4-1-with-security-simple)

<br/>



## WebSocket 예제

- [Eureka + Gateway + websocket](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-websocket)

<br/>



## Eureka 없이 Gateway 구성 예제 

- [Gateway + MSA 서비스](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-non-eureka-msa-services)
- k8s 로 전환한다면 Eureka 가 거의 필요가 없어짐

<br/>



## Spring Cloud 와 연동한 예제

- [Eureka 와 연동](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-msa-services)
- [Eureka + zipkin + Gateway](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-msa-services-zipkin)
- [Eureka + Gateway + websocket](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-websocket)
  - k8s 에서 쓴다면 service-discovery 를!!!

<br/>



# 문서

> - 예제만 만들어뒀지, 문서를 작업한게 별로 없는 상태
> - 문서작업 역시 해야 하는데 까먹고 있다가 열어보고 문서정리를 너무 안해둬서 충격받은 상태...
> - 내가 열심히하고 있다고 착각하지 말아야겠다. 아직 부족하다구

정리 예정<br/>







<br/>





# 예제+문서 작업 계획

rate limiter

- Redis Rate Limiter

k8s (at kind(local develop))

- k8s 내에서 service-discovery 없이 gateway 구성
- k8s 내에서 rate limiter, 성능측정 문서

