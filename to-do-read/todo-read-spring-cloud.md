## to read

프로젝트에 바로 도입하고 뭐 이럴 건 아니고 지속적으로 읽어보고 문서화작업을 할 내용들<br/>



Spring Cloud Config

- [Spring Cloud Config 도입하기 및 private 리포지터리 SSL로 연결 설정 및 privateKey 암호화](https://mangkyu.tistory.com/253)

<br/>



Spring Cloud Gateway

- [Spring Cloud Gateway - cheese10yun](https://cheese10yun.github.io/spring-cloud-gateway/)
- [Spring Cloud Gateway RewritePath 사용해 uri 수정](https://m.blog.naver.com/qjawnswkd/222311687196)
- [Spring Cloud 를 이용한 MSA 2 - Gateway](https://velog.io/@haerong22/Spring-Cloud-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-MSA-2.-Gateway)

<br/>



Spring Cloud Gateway + Websocket

- [Load Balanced Websockets with Spring Cloud Gateway](https://www.springcloud.io/post/2022-03/load-balanced-websockets-with-spring-cloud-gateway/#gsc.tab=0)
  - 예제 Github : [github.com/jmlw/demo-projects](https://github.com/jmlw/demo-projects/tree/master)
- [Kafka 로 웹 소켓 브로커들을 Eureka Server 에 등록해서 통신하는 예제](https://velog.io/@hongjunland/Spring-Boot-Kafka-Spring-Boot-WebSocket-%EC%B1%84%ED%8C%85-%EC%84%9C%EB%B2%84-Scale-out-%EC%97%B0%EB%8F%99)

<br/>





Service Discovery(Eureka Server) 를 분산 서비스로 구성

- [11번가 : Service Discovery DR 구성 1부 - Eureka 서버를 지역 분산시켜 안정성을 높이자](https://11st-tech.github.io/2022/12/30/eureka-disaster-recovery-1/)
- [Eureka (Spring Cloud Discovery)](https://velog.io/@seowj0710/Spring-Boot-Eureka)

<br/>





Spring Cloud Gateway Rate Limiter

- devs0n.tistory.com

  - [개요](https://devs0n.tistory.com/68)

  - [1\. 기본 프로젝트 생성](https://devs0n.tistory.com/69)

  - [2\. RequestRateLimiter 필터 적용](https://devs0n.tistory.com/70)

  - [3\. RateLimiterFilter 파헤치기](https://devs0n.tistory.com/71)

  - [부록. CustomFilter 만들기](https://devs0n.tistory.com/72)

- python 기반 자료
  - [Rate Limit 이란?](https://etloveguitar.tistory.com/126)
  - [Leaky Bucket 알고리즘 구현 (rate limiting)](https://etloveguitar.tistory.com/127)
  - [Token Bucket 알고리즘 구현 (rate limiting)](https://etloveguitar.tistory.com/128)
  - [Fixed Window 알고리즘 구현 (rate limiting)](https://etloveguitar.tistory.com/129)
  - [Sliding Window 알고리즘 구현](https://etloveguitar.tistory.com/130)

- 지마켓 기술 블로그
  - [Redis Lua Script 를 이용해서 API Rate Limiter 개발](https://dev.gmarket.com/69)
  - [Quick Guide to the Guava RateLimiter](https://www.baeldung.com/guava-rate-limiter)
- dgle.dev
  - [Request Rate Limiter 를 만들어보자 1편](https://dgle.dev/RateLimiter1/)
  - [Request Rate Limiter 를 만들어보자 2편](https://dgle.dev/RateLimiter2/)

- dev.to
  - [Token Bucket Algorithm](https://dev.to/satrobit/rate-limiting-using-the-token-bucket-algorithm-3cjh)
- woooongs.tistory.com
  - [Spring Cloud Gateway RateLimiter 적용](https://woooongs.tistory.com/56)

- white-polarbear.tistory.com
  - [QoS Token Bucket Algorithms](https://white-polarbear.tistory.com/63)

- 도움이 많이 되었던 자료
  - [Spring Boot + Kotlin : Redis Lua Script 적용](https://junuuu.tistory.com/890)
  - [Redis Lua Script 를 이용해서 API Rate Limiter 개발](https://dev.gmarket.com/69)
  - [Atomic 처리와 cache stampede 대책을 위해 Redis Lua script 를 활용한 이야기](https://engineering.linecorp.com/ko/blog/atomic-cache-stampede-redis-lua-script)

<br/>



Spring Cloud Kubernetes 1

- [Spring Cloud Kubernetes](https://cloud.spring.io/spring-cloud-kubernetes/spring-cloud-kubernetes.html)
- [Spring Cloud Kubernetes with Spring Boot 3](https://piotrminkowski.com/2023/06/08/spring-cloud-kubernetes-with-spring-boot-3/)
- [Sample Spring Microservices Kubernetes](https://github.com/piomin/sample-spring-microservices-kubernetes)
- [Best Practices for Java Apps on Kubernetes](https://piotrminkowski.com/2023/02/13/best-practices-for-java-apps-on-kubernetes/)



Spring Cloud kubernetes 2

- [cloud.spring.io - Spring Cloud Kubernetes](https://cloud.spring.io/spring-cloud-kubernetes/index.html)
  - [Why do you need Spring Cloud Kubernetes?](https://cloud.spring.io/spring-cloud-kubernetes/spring-cloud-kubernetes.html)

- [docs.spring.io - Spring Cloud Kubernetes](https://docs.spring.io/spring-cloud-kubernetes/reference/index.html)
  - [Starters](https://docs.spring.io/spring-cloud-kubernetes/reference/getting-started.html)
    - Starters are convenient dependency descriptors you can include in your application. Include a starter to get the dependencies and Spring Boot auto-configuration for a feature set. Starters that begin with `spring-cloud-starter-kubernetes-fabric8` provide implementations using the [Fabric8 Kubernetes Java Client](https://github.com/fabric8io/kubernetes-client). Starters that begin with `spring-cloud-starter-kubernetes-client` provide implementations using the [Kubernetes Java Client](https://github.com/kubernetes-client/java).
    - Kubernetes 에서 지원하는 Java Client 와 Fabric8 에서 지원하는 Java Client 가 따로 존재하는 듯 해보인다. 즉 구현체로 Kubernetes 에서 지원하는 Java Client, Fabric8 에서 지원하는 Java Client 가 존재하는 것으로 보임.

  - [Spring Cloud Kubernetes Discovery Server](https://docs.spring.io/spring-cloud-kubernetes/reference/spring-cloud-kubernetes-discoveryserver.html)
    - 




<br/>



> **클라이언트 라이브러리로 Kubernetes 애플리케이션 개발하기**
>
> - 참고 : https://aigishoo.tistory.com/19
>
> API Machinery SIG (special interest group) 에서 지원하는 두 개의 쿠버네티스 API 클라이언트 라이브러리가 있습니다. 
>
> - 고랭 클라이언트 : https://github.com/kubernetes/client-go
> - 파이썬 : https://github.com/kubernetes-client/python
>
> 그 외 아래와 같은 사용자 라이브러리도 있습니다.
>
> - Fabric8 자바 클라이언트 : https://github.com/fabric8io/kubernetes-client

<br/>



Fabric 8

- [spring.fabric8.io - Fabric8 Spring](https://spring.fabric8.io/)
- [github.com/fabric8io/spring-cloud-kubernetes](https://github.com/fabric8io/spring-cloud-kubernetes)
- [github.com/fabric8io/spring-cloud-kubernetes - KubernetesDiscoveryClient](https://github.com/fabric8io/spring-cloud-kubernetes/blob/master/spring-cloud-kubernetes-discovery/src/main/java/io/fabric8/spring/cloud/discovery/KubernetesDiscoveryClient.java)
- [Difference between Fabric8 and Official Kubernetes Java Client](https://itnext.io/difference-between-fabric8-and-official-kubernetes-java-client-3e0a994fd4af)
- [jason-heo.github.io - fabric8 kubernetes API 사용법 예제](https://jason-heo.github.io/programming/2021/05/29/fabric8-k8s-api-example.html)

<br/>



Fabric 8 test

- [jason-heo.github.io - fabric8 kubernetes test 사용법 (mocking kubernetes API Server)](https://jason-heo.github.io/programming/2021/09/27/fabric8-mock-server.html)

- [How to write tests with Fabric8 Kubernetes Client](https://developers.redhat.com/articles/2023/01/24/how-write-tests-fabric8-kubernetes-client)

- [Mock Kubernetes API server in Java using Fabric8 Kubernetes Mock Server](https://itnext.io/mock-kubernetes-api-server-in-java-using-fabric8-kubernetes-mock-server-81a75cf6c47c)

- [Fabric8: Mock Kubernetes Server doesn't actually create a resource without 'expect' set](https://stackoverflow.com/questions/74506037/fabric8-mock-kubernetes-server-doesnt-actually-create-a-resource-without-expe)

<br/>



[network.pivotal.io - Spring Cloud Gateway for Kubernetes](https://network.pivotal.io/products/spring-cloud-gateway-for-kubernetes)

[spring.io/blog - Spring Cloud Gateway for Kubernetes](https://spring.io/blog/2021/05/04/spring-cloud-gateway-for-kubernetes)









