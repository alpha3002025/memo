## Spring Cloud Gateway Overview

아직 문서 작업 중 이에요!! 빠르게 정리하겠습니다!!!!!!!!!!!!!!!!!!!!!!!<br/>



## 참고

Reference Docs

- [docs.spring.io - Spring Cloud Gateway Docs](https://docs.spring.io/spring-cloud-gateway/reference/)

- [cloud.spring.io - Spring Cloud Gateway Docs](https://cloud.spring.io/spring-cloud-gateway/reference/html/)
- 이 문서는 [cloud.spring.io - Spring Cloud Gateway Docs](https://cloud.spring.io/spring-cloud-gateway/reference/html/) 의 문서를 기반으로 예제들을 작성했습니다.

Predicate

- [5.1. The After Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-request-predicates-factories)

- [5.2. The Before Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-before-route-predicate-factory)

- [5.3. The Between Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-between-route-predicate-factory)

- [5.4. The Cookie Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-cookie-route-predicate-factory)

- [5.5. The Header Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-header-route-predicate-factory)

- [5.6. The Host Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-host-route-predicate-factory)

- [5.7. The Method Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-method-route-predicate-factory)

- [5.8. The Path Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-path-route-predicate-factory)

- [5.9. The Query Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-query-route-predicate-factory)

- [5.10. The RemoteAddr Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-remoteaddr-route-predicate-factory)

- [5.11. The Weight Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-weight-route-predicate-factory)

[GlobalFilters](https://cloud.spring.io/spring-cloud-gateway/reference/html/#global-filters)

- [7.1. Combined Global Filter and GatewayFilter Ordering](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-combined-global-filter-and-gatewayfilter-ordering)
- [7.2. Forward Routing Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#forward-routing-filter)
- [7.3. The LoadBalancerClient Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-loadbalancerclient-filter)
- [7.4. The ReactiveLoadBalancerClientFilter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#reactive-loadbalancer-client-filter)
- [7.5. The Netty Routing Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-netty-routing-filter)
- [7.6. The Netty Write Response Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-netty-write-response-filter)
- [7.7. The RouteToRequestUrl Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-routetorequesturl-filter)
- [7.8. The Websocket Routing Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-websocket-routing-filter)
- [7.9. The Gateway Metrics Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-gateway-metrics-filter)
- [7.10. Marking An Exchange As Routed](https://cloud.spring.io/spring-cloud-gateway/reference/html/#marking-an-exchange-as-routed)


- https://cheese10yun.github.io/spring-cloud-gateway/

[HttpHeadersFilters](https://cloud.spring.io/spring-cloud-gateway/reference/html/#httpheadersfilters)

- [8.1. Forwarded Headers Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#forwarded-headers-filter)
- [8.2. RemoveHopByHop Headers Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#removehopbyhop-headers-filter)
- [8.3. XForwarded Headers Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#xforwarded-headers-filter)

[TLS and SSL](https://cloud.spring.io/spring-cloud-gateway/reference/html/#tls-and-ssl)

- [9.1. TLS Handshake](https://cloud.spring.io/spring-cloud-gateway/reference/html/#tls-handshake)

RouteDefinitionLocator 란 ?

- [10\. Configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#configuration)

Route 에 부가적인 메타데이터 프로퍼티 사용

- [11\. Route Metadata Configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#route-metadata-configuration)

Http Timeout 설정

- [12\. Http timeouts configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#http-timeouts-configuration)

  - [12.1. Global timeouts](https://cloud.spring.io/spring-cloud-gateway/reference/html/#global-timeouts)

  - [12.2. Per-route timeouts](https://cloud.spring.io/spring-cloud-gateway/reference/html/#per-route-timeouts)

  - [12.3. Fluent Java Routes API](https://cloud.spring.io/spring-cloud-gateway/reference/html/#fluent-java-routes-api)

  - [12.4. The DiscoveryClient Route Definition Locator](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-discoveryclient-route-definition-locator)


<br/>

Reactor Netty Access Log

- [13\. Reactor Netty Access Log](https://cloud.spring.io/spring-cloud-gateway/reference/html/#reactor-netty-access-logs)

CORS

- [14\. CORS Configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#cors-configuration)

Actuator API<br/>

- [15\. Actuator API](https://cloud.spring.io/spring-cloud-gateway/reference/html/#actuator-api)

  - [15.1. Verbose Actuator Format](https://cloud.spring.io/spring-cloud-gateway/reference/html/#verbose-actuator-format)

  - [15.2. Retrieving Route Filters](https://cloud.spring.io/spring-cloud-gateway/reference/html/#retrieving-route-filters)

  - [15.3. Refreshing the Route Cache](https://cloud.spring.io/spring-cloud-gateway/reference/html/#refreshing-the-route-cache)

  - [15.4. Retrieving the Routes Defined in the Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/#retrieving-the-routes-defined-in-the-gateway)

  - [15.5. Retrieving Information about a Particular Route](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-retrieving-information-about-a-particular-route)

  - [15.6. Creating and Deleting a Particular Route](https://cloud.spring.io/spring-cloud-gateway/reference/html/#creating-and-deleting-a-particular-route)

  - [15.7. Recap: The List of All endpoints](https://cloud.spring.io/spring-cloud-gateway/reference/html/#recap-the-list-of-all-endpoints)

<br/>



## 예제 github

예제로 사용한 Gateway 는 모두 Reactive Gateway 를 사용했습니다.

- [Eureka 없이 구성한 Gateway + MSA](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-non-eureka-msa-services)
- [Eureka + Gateway + MSA](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-msa-services)
- [Eureka + Gateway + Websocket WAS (Servlet 버전)](https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-websocket)

<br/>



예제 구성과 소스코드에 대한 설명은 이 문서의 제일 마지막에 정리해두었습니다.<br/>

<br/>



## Route, Predicate, Filters

### Route

- 특정 URI 에 대해서 어떤 조건(Predicate)일 때 어떤 Filter 를 적용할 지에 대한 URL을 매핑하는 규칙의 단위입니다. 이 규칙의 단위를 하나의 Route 객체로 표현합니다. 
- 주로 application.yml 내의 `spring.cloud.routes[i]` 에 하나씩 정의합니다.
- 각각의 Route 는 고유한 id 를 이용해서 각각의 Route 들을 구분할 수 있습니다. `spring.cloud.routes[i].id={라우트ID}` 로 정의합니다.
- Route 는 uri, predicate, filter 로 구분합니다.

<br/>



### Predicate

- 어떤 요청에 대해 헤더, URI 패턴 등에 대해 조건값이 맞는지를 판단하는 조건식 역할을 하는 객체입니다.
- Path, Host, Method 에 어떤 값이 왔을때 Gateway Filter 를 타게 할지를 결정하도록 하는 조건문같은 역할을 수행합니다.
- 주로 application.ym 내의 `spring.cloud.routes[i].predicate` 에 정의합니다. 
- Path의 `**`, `*` 표현식
  - e.g. `Path=/order-service/**` :  `{uri}/order-service/{경로}` 에서  `/order-service`하위의 모든 Path 에 대해 Filter 를 적용하겠다는 의미입니다.
  - e.g. `Path=/order-service/*` :  `{uri}/order-service/{경로}` 에서  `/order-service`하위의 첫번째 Path 에 대해 Filter 를 적용하겠다는 의미입니다.

- Host, Query Parameter, Path Variable, Method 와 같은 REST API 의 주요 요청 형식에 대해 조건문을 만들어서 적용하는 것이 가능합니다.

<br/>



참고

- [5.1. The After Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-request-predicates-factories)

- [5.2. The Before Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-before-route-predicate-factory)

- [5.3. The Between Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-between-route-predicate-factory)

- [5.4. The Cookie Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-cookie-route-predicate-factory)

- [5.5. The Header Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-header-route-predicate-factory)

- [5.6. The Host Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-host-route-predicate-factory)

- [5.7. The Method Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-method-route-predicate-factory)

- [5.8. The Path Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-path-route-predicate-factory)

- [5.9. The Query Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-query-route-predicate-factory)

- [5.10. The RemoteAddr Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-remoteaddr-route-predicate-factory)

- [5.11. The Weight Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-weight-route-predicate-factory)

<br/>



#### After, Before, Between Predicate Factory

After, Before, Between Predicate는 요청이 특정 시점 이전,이후,사이에 발생했는지를 검사하는 Predicate 역할을 수행합니다.

- After : 특정 시점 이 후에 발생한 요청인지를 검사해서 특정 시점 이후의 요청일 경우 true
- Before : 특정 시점 이 전에 발생한 요청인지를 검사해서 특정 시점 이전의 요청일 경우 true
- Between 특정 시점 사이에 발생한 요청인지를 검사해서 특정시점 사이의 요청일 경우 true

<br/>

이벤트 등에 이런 시간 관련된 Filter 를 사용하면 유용합니다. <br/>

예제를 한번씩 꼭 수행해보셨으면 합니다. 한번씩 실행해보고 경험을 실제로 하고나면 나중에 더 기억이 잘 나기 때문입니다.<br/>

<br/>



##### After

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order/**
          - After=2024-08-23T22:28:28.228+09:00[Asia/Seoul]

  # ...
```

<br/>

만약 현재 시각이 위에서 적은 `After=2024-08-23T22:28:28.228+09:00[Asia/Seoul]` 에 명시한 시간의 이전이라면 아래와 같은 에러가 출력됩니다.

```plain
HTTP/1.1 404 Not Found
Content-Type: application/json
Content-Length: 133

{
  "timestamp": "2024-08-23T11:56:15.644+00:00",
  "path": "/order-service/hello",
  "status": 404,
  "error": "Not Found",
  "requestId": "c03b66a3-1"
}
```

<br/>



##### Before

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order/**
          - Before=2024-08-23T22:28:28.228+09:00[Asia/Seoul]

  # ...
```

<br/>

만약 현재 시각이 위에서 적은 `Before=2024-08-23T22:28:28.228+09:00[Asia/Seoul]` 이 되기 전이라면 아래와 같이 정상적인 결과를 내게 됩니다.

```plain
HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Date: Fri, 23 Aug 2024 11:58:35 GMT

hello

Response code: 200 (OK); Time: 377ms (377 ms); Content length: 5 bytes (5 B)
```

<br/>



##### Between

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order/**
          - Between=2024-08-22T22:28:28.228+09:00[Asia/Seoul], 2024-08-23T22:28:28.228+09:00[Asia/Seoul]

  # ...
```

만약 현재 시각이 위에서 적은 `2024-08-22T22:28:28.228+09:00[Asia/Seoul], 2024-08-23T22:28:28.228+09:00[Asia/Seoul]` 사이의 기간이라면 아래와 같이 정상적인 결과를 내게 됩니다.

<br/>

```plain
HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Date: Fri, 23 Aug 2024 11:59:58 GMT

hello

Response code: 200 (OK); Time: 373ms (373 ms); Content length: 5 bytes (5 B)
```

<br/>



#### Cookie, Header Predicate Factory

##### Cookie

application.yml 에 아래와 같이 정의하는 경우를 확인해봅니다.

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order/**
          - Cookie=victoria, 222
  # ...
```

victoria 라는 name 에 대해 222 라는 value가 Cookie 로 요청으로 전달되면 통과되는 Predicate 입니다.

**value 에는 정규표현식을 사용하는 것 역시 가능합니다.**

<br/>

위 요청은 아래와 같이 요청을 보내면 200 OK 응답을 받게 됩니다.

```http
GET http://localhost:8080/order-service/hello
Cookie: victoria=222

```

<br/>



##### Header

application.yml 에 아래와 같이 정의하는 경우를 확인해봅니다.

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order/**
          - Header=X-Request-id, \d+
  # ...
```

`X-Request-id` 라는 헤더에 대해 숫자 여러개를 의미하는 정규표현식 `\d+` 에 해당하는 숫자열이 value 가 헤더로 전달되면 200 OK 를 응답으로 받게 됩니다.

```plain
### order-service
GET http://localhost:8080/order-service/hello
X-Request-id: 32
```

<br/>



#### Host, Method, Path, Query Predicate Factory

##### Host

Host 는 Ant 스타일의 패턴 매칭을 사용합니다.

application.yml 을 아래와 같이 적용된 경우를 살펴봅니다.

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order-service/**
          - Host=**.reddit.com, **.instagram.com
  # ...
```

`Host` 라는 Name 에 대해 Value 로 `mail.reddit.com, api.instagram.com` 을 지정해서 요청을 보내면 200 OK 응답을 받게 됩니다.

```http
### order-service
GET http://localhost:8080/order-service/hello
Host: mail.reddit.com, api.instagram.com
```

<br/>



##### Method

application.yml 을 아래와 같이 적용된 경우를 살펴봅니다.

위에서 살펴본 Host 헤더와 함께 사용했습니다.

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=/order/**
          - Host=**.reddit.com, **.instagram.com
          - Method=GET,POST
  # ...
```

Method 라는 Name 에 대해 `GET, POST` 를 Value 로 받게끔 했는데, 이렇게 지정하면 GET, POST 메서드 요청만을 허용하게 됩니다. 만약 `DELETE` 를 허용하지 않고 `GET, POST` 만을 허용해야만 하는 MSA  등에 유용하게 적용할 수 있습니다.<br/>

아래의 http 요청은 200 OK 를 응답합니다.

```http
### order-service
GET http://localhost:8080/order-service/hello
Host: mail.reddit.com, api.instagram.com
```

<br/>



##### Path

- 공식문서 내용 설명
- !(not) 연산자 적용 가능 여부
- 끝에 `/` 이 안 붙는 경우만을 Predicate 할 경우 (false 값을 부여)

<br/>

**공식문서 내용 설명**<br/>

제 경우에는 이 Path 관련 기능은 언제 사용하면 좋을지 아직 찾지는 못했습니다. 공식 문서에서 제공하는 application.yml 예제는 다음과 같습니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: path_route
        uri: https://example.org
        predicates:
        - Path=/red/{segment},/blue/{segment}
```

만약 이렇게 route 를 정의하면 request 경로가 아래와 같을 경우에 특정 Path 에 대한 요청을 Predicate 할 수 있습니다.

- `/red/1` 
- `/red/blue` 
-  `/blue/green`

위에서 사용한 `segment` 라는 값은 원하는대로 `path` 라는 값을 써도 되고 `id` 라는 변수를 써도 되고, 회사 내에서 원하는 규칙에 대한 정의에 맞게 이름을 정의해주시면 됩니다.<br/>

Predicate 에서 사용한 변수 segment 는 ServerWebExchange.getAttributes() 라는 메서드로 접근가능하며 예제는 아래와 같습니다. 자세한 내용은 [The Path Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-path-route-predicate-factory) 를 참고해주시기 바랍니다.<br/>

```java
Map<String, String> uriVariables = ServerWebExchangeUtils.getPathPredicateVariables(exchange);

String segment = uriVariables.get("segment");
```

<br/>



**!(not) 연산자 적용 가능 여부**<br/>

제 경우에는 아래 예제를 통해 !(not)연산자가 적용이 되는지를 테스트해봤는데, 아무런 효과는 없었습니다.

```yaml
spring:
  # ... 
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: http://localhost:8081
        predicates:
          - Path=!/order-service/order/admin/{path}
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
  # ...
```

<br/>



**끝에 `/` 이 없는 것을 기준으로 Predicate 하려고 할 경우**

- 참고 : [What's the use of matchOptionalTrailingSeparator in Spring Cloud Gateway Predicate](https://stackoverflow.com/questions/62493309/whats-the-use-of-matchoptionaltrailingseparator-in-spring-cloud-gateway-predica)



만약 아래와 같이 정의하면 `/foo/{segment}`, `/foo/{segment}/` 가 모두 허용됩니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: https://example.org
        predicates:
        - Path=/foo/{segment}
```

<br/>



만약 아래와 같이 정의하면 `/foo/segment` 하나만 허용됩니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: https://example.org
        predicates:
        - Path=/foo/{segment},false
```

<br/>



##### Query

참고 : [5.9 The Query Route Predicate Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-query-route-predicate-factory)

application.yml 의 내용은 다음과 같습니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: https://example.org
        predicates:
          - Query=itemName
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
```

<br/>

이 내용을 직접 실행해보는 http 파일의 내용은 다음과 같습니다. `?itemName=Galaxy` 에 대해서 200 OK 의 응답을 냅니다.

```http
GET http://localhost:8080/order-service/hello?itemName=Galaxy

HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Date: Sat, 24 Aug 2024 11:25:46 GMT

hello
```

<br/>

이번에는 query param 의 특정 value 에 대해서만 200 OK 를 내도록 application.yml 을 작성해봅니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: https://example.org
        predicates:
          - Query=country, Korea
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
```

<br/>

이 내용을 직접 실행해보는 http 파일의 내용은 다음과 같습니다. `?country=Korea` 에 대해서만 200 OK 의 응답을 냅니다.

```http
GET http://localhost:8080/order-service/hello?country=Korea

HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Date: Sat, 24 Aug 2024 11:31:32 GMT

hello
```

<br/>

만약 `?country=Africa` 라는 파라미터 조합처럼 `?country=Korea` 가 아닌 파라미터로 요청을 보내면 다음과 같이 404 응답을 냅니다.

```http
GET http://localhost:8080/order-service/hello?country=Africa

HTTP/1.1 404 Not Found
Content-Type: application/json
Content-Length: 133

{
  "timestamp": "2024-08-24T11:31:52.046+00:00",
  "path": "/order-service/hello",
  "status": 404,
  "error": "Not Found",
  "requestId": "f407ee15-3"
}
```

<br/>

#### RemoteAddr, Weight Predicate Factory

application.yml 내의 설정을 아래와 같이 했습니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: https://example.org
        predicates:
          - Query=country, Korea
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
          - AddRequestHeader=X-Request-statistics, order-service
# ...
```





### Filter

- HTTP  Request, Response 에 대한 Filter 역할을 수행합니다. Servlet 의 Filter 라기 보다는 조금 더 앞단에서의 동작하는 Gateway 서버가 인식할수 있는 Filter 의 개념으로 볼 수 있습니다.
- Route 내에 filter 를 두어서 특정 Route 에 대해 특정 Filter 가 동작되도록 지정할 수 있습니다.
- RewritePath 등을 지정할 때 Filter 를 사용합니다.
- 주로 application.yml 내의 `spring.cloud.routes[i].filters` 에 정의합니다.

<br/>



Filter 는 여러가지 종류가 있습니다. 약 40개 종류의 필터가 있습니다.<br/>

각각의 Filter 들에 대해서는 별도의 문서에 정리예정입니다.<br/>

이번 문서에서는 간단하게 몇몇 종류의 필터만을 정리해봅니다.<br/>

<br/>





## TLS and SSL

[TLS and SSL](https://cloud.spring.io/spring-cloud-gateway/reference/html/#tls-and-ssl)

- [9.1. TLS Handshake](https://cloud.spring.io/spring-cloud-gateway/reference/html/#tls-handshake)

<br/>



## RouteDefinitionLocator 란 ?

[10\. Configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#configuration)

<br/>



## Route 에 부가적인 메타데이터 프로퍼티 사용

[11\. Route Metadata Configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#route-metadata-configuration)

<br/>



## Http Timeout 설정

[12\. Http timeouts configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#http-timeouts-configuration)

- [12.1. Global timeouts](https://cloud.spring.io/spring-cloud-gateway/reference/html/#global-timeouts)
- [12.2. Per-route timeouts](https://cloud.spring.io/spring-cloud-gateway/reference/html/#per-route-timeouts)
- [12.3. Fluent Java Routes API](https://cloud.spring.io/spring-cloud-gateway/reference/html/#fluent-java-routes-api)
- [12.4. The DiscoveryClient Route Definition Locator](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-discoveryclient-route-definition-locator)

<br/>



## Reactor Netty Access Log

[13\. Reactor Netty Access Log](https://cloud.spring.io/spring-cloud-gateway/reference/html/#reactor-netty-access-logs)

<br/>



## CORS

[14\. CORS Configuration](https://cloud.spring.io/spring-cloud-gateway/reference/html/#cors-configuration)

<br/>



## Actuator API

[15\. Actuator API](https://cloud.spring.io/spring-cloud-gateway/reference/html/#actuator-api)

- [15.1. Verbose Actuator Format](https://cloud.spring.io/spring-cloud-gateway/reference/html/#verbose-actuator-format)
- [15.2. Retrieving Route Filters](https://cloud.spring.io/spring-cloud-gateway/reference/html/#retrieving-route-filters)
- [15.3. Refreshing the Route Cache](https://cloud.spring.io/spring-cloud-gateway/reference/html/#refreshing-the-route-cache)
- [15.4. Retrieving the Routes Defined in the Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/#retrieving-the-routes-defined-in-the-gateway)
- [15.5. Retrieving Information about a Particular Route](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-retrieving-information-about-a-particular-route)
- [15.6. Creating and Deleting a Particular Route](https://cloud.spring.io/spring-cloud-gateway/reference/html/#creating-and-deleting-a-particular-route)
- [15.7. Recap: The List of All endpoints](https://cloud.spring.io/spring-cloud-gateway/reference/html/#recap-the-list-of-all-endpoints)

<br/>



## 예제

이번 문서에서는 예제를 두 종류로 구성합니다.

- (1) : Discovery Server (Eureka Server) 없이 Gateway 와 MSA 를 연결한 버전
  - api-gateway
  - order-api
  - payment-api
- (2) : Discovery Server (Eureka Server) 와 함께 Gateway, MSA 를 연결한 버전
  - service-discovery
  - api-gateway
  - order-api
  - payment-api

<br/>



## (1) Gateway,MSA (non Eureka)

example link 

- https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-non-eureka-msa-services

example memo 

- https://github.com/chagchagchag/memo/blob/main/spring-gateway/recipe-gateway-msa-non-eureka.md

<br/>

![](./img/gateway-concept-and-basic-example-with-eureka/2-first-example.png)

<br/>





## (2) Gateway, MSA + Eureka

example link

- https://github.com/chagchagchag/memo/tree/main/spring-gateway/example/gateway-eureka-msa-services

example memo

- https://github.com/chagchagchag/memo/blob/main/spring-gateway/recipe-gateway-msa-eureka.md





## HTTP Timeout



## zipkin



## Feign



## elasticsearch + zipkin + kibana



