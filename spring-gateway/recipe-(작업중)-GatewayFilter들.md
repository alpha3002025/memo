# Gateway 의 Filter 란?

- HTTP  Request, Response 에 대한 Filter 역할을 수행합니다. Servlet 의 Filter 라기 보다는 조금 더 앞단에서의 동작하는 Gateway 서버가 인식할수 있는 Filter 의 개념으로 볼 수 있습니다.
- Route 내에 filter 를 두어서 특정 Route 에 대해 특정 Filter 가 동작되도록 지정할 수 있습니다.
- RewritePath 등을 지정할 때 Filter 를 사용합니다.
- 주로 application.yml 내의 `spring.cloud.routes[i].filters` 에 정의합니다.

<br/>

Spring Cloud Gateway 에서 기본으로 지원하는 Filter 는 여러 종류들이 있습니다. 

- [6.1. The AddRequestHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-addrequestheader-gatewayfilter-factory)
- [6.2. The AddRequestParameter GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-addrequestparameter-gatewayfilter-factory)
- [6.3. The AddResponseHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-addresponseheader-gatewayfilter-factory)

- [6.4. The DedupeResponseHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-deduperesponseheader-gatewayfilter-factory)
- [6.5. Spring Cloud CircuitBreaker GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#spring-cloud-circuitbreaker-filter-factory)
- [6.6. The FallbackHeaders GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#fallback-headers)
- [6.7. The MapRequestHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-maprequestheader-gatewayfilter-factory)
- [6.8. The PrefixPath GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-prefixpath-gatewayfilter-factory)
- [6.9. The PreserveHostHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-preservehostheader-gatewayfilter-factory)
- [6.10. The RequestRateLimiter GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-requestratelimiter-gatewayfilter-factory)
- [6.11. The RedirectTo GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-redirectto-gatewayfilter-factory)
- [6.12. The RemoveRequestHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-removerequestheader-gatewayfilter-factory)
- [6.13. RemoveResponseHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#removeresponseheader-gatewayfilter-factory)
- [6.14. The RemoveRequestParameter GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-removerequestparameter-gatewayfilter-factory)
- [6.15. The RewritePath GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-rewritepath-gatewayfilter-factory)
- [6.16. RewriteLocationResponseHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#rewritelocationresponseheader-gatewayfilter-factory)
- [6.17. The RewriteResponseHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-rewriteresponseheader-gatewayfilter-factory)
- [6.18. The SaveSession GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-savesession-gatewayfilter-factory)
- [6.19. The SecureHeaders GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-secureheaders-gatewayfilter-factory)
- [6.20. The SetPath GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-setpath-gatewayfilter-factory)
- [6.21. The SetRequestHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-setrequestheader-gatewayfilter-factory)
- [6.22. The SetResponseHeader GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-setresponseheader-gatewayfilter-factory)
- [6.23. The SetStatus GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-setstatus-gatewayfilter-factory)
- [6.24. The StripPrefix GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-stripprefix-gatewayfilter-factory)
- [6.25. The Retry GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-retry-gatewayfilter-factory)
- [6.26. The RequestSize GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-requestsize-gatewayfilter-factory)
- [6.27. The SetRequestHost GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-setrequesthost-gatewayfilter-factory)
- [6.28. Modify a Request Body GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#modify-a-request-body-gatewayfilter-factory)
- [6.29. Modify a Response Body GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#modify-a-response-body-gatewayfilter-factory)
- [6.30. Default Filters](https://cloud.spring.io/spring-cloud-gateway/reference/html/#default-filters)

<br/>



여러 종류의 Filter 들 중에서 AddRequestHeader, AddRequestParameter, AddResponseHeader, SecureHeaders, Retry, RewritePath, RewriteLocationResponseHeader, RewriteResponseHeader , SetRequestHeader, SetStatus, RequestSize 를 골라서 예제를 정리합니다.

- RateLimiter 는 별도의 문서에서 정리합니다.
- CircuitBreaker, Retry, FallbackHeaders 는 별도의 문서에서 정리합니다.

<br/>



이 외에도 RequestBody, Response Body Gateway filter 를 수정하는 방법에 대해서도 정리해보겠습니다.

- [6.28. Modify a Request Body GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#modify-a-request-body-gatewayfilter-factory)
- [6.29. Modify a Response Body GatewayFilter Factory](https://cloud.spring.io/spring-cloud-gateway/reference/html/#modify-a-response-body-gatewayfilter-factory)

이 외에 자세한 내용은 반드시 아래의 링크에서 살펴봐야 합니다.

- https://cloud.spring.io/spring-cloud-gateway/reference/html/#gatewayfilter-factories 

<br/>



# Gateway Filter

## AddRequestHeader

아래와 같은 API 가 있다고 해보겠습니다.

```java
public class OrderApi{
    
  // ...
  
  @GetMapping("/order/statistics/{orderPath}")
  public ResponseEntity<String> statistics(
      @PathVariable("orderPath") String orderPath,
      HttpServletRequest request
  ) {
    String header = request.getHeader("X-Request-statistics");
    logger.info("header = {}", header);
    return ResponseEntity.status(HttpStatus.OK).body(orderPath);
  }
  
  // ... 
}
```

<br/>



그리고 Gateway 애플리케이션 내에 선언한 application.yml 에서는 아래와 같이 Filter 를 정의하고 있습니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: order-service-header-filter-example
        uri: http://localhost:8081
        order: 1
        predicates:
		  - Path=/order-service/order/statistics/**
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
          - AddRequestHeader=X-Request-statistics, order-service
# ...
```

<br/>



http 파일에서 아래와 같이 요청을 보내도록 작성 후 요청을 보내봅니다.

```http
### order-service/order/statistics
GET http://localhost:8080/order-service/order/statistics/card
```

<br/>



이렇게 하면 http 파일에서도 브라우저에서도 헤더가 추가됐는지 확인은 불가능합니다. 클라이언트에서는 헤더를 추가한 적이 없기 때문입니다. 하지만, order-service 의 서버 로그를 보면 서버측에서 request 헤더가 추가되었음을 확인 가능합니다. 이 Header 는 클라이언트가 아니라 Gateway 가 order-service 의 앞 단에서 추가한 Header 입니다.<br/>

서버 로그는 아래와 같이 나타납니다. (위의 컨트롤러 코드를 자세히 보시면 log 를 찍고 있습니다.)

```java
2024-08-24T22:33:50.710+09:00  INFO 1162004 --- [order-service] [nio-8081-exec-9] i.c.example.order.domain.OrderApi        : header = order-service
```

<br/>



**URI Variable 을 적용하기**<br/>

URI Variable 을 적용해서 특정 URI 의 뒤에 명시한 변수에 해당하는 값으로 치환해서 값을 대입해주는 것 역시 가능합니다. 이번예제에서는 위의 예제에서의 OrderApi.java 는 그대로 두고 Gateway 의 application.yml 을 아래와 같이 작성합니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: order-service-header-filter-example
        uri: http://localhost:8081
        order: 1
        predicates:
		  - Path=/order-service/order/statistics/{orderPath}
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
          - AddRequestHeader=X-Request-statistics, Statistics-{orderPath}
# ...
```

<br/>



서버 로그에서는 `Statistcs-{orderPath}` 에 서 `orderPath` 에 해당하는 값으로 치환되어서 로그가 찍힌 것을 확인 가능합니다.

```plain
2024-08-24T22:35:08.487+09:00  INFO 1162004 --- [order-service] [nio-8081-exec-1] i.c.example.order.domain.OrderApi        : header = Statistics-card
```

<br/>



## AddRequestParameter

이번에는 Header 가 아닌 Parameter 에 대해 RequestParameter 를 추가하는 예제입니다. 이전 예제에서도 설명했듯 클라이언트에서 추가하는 것이 아니라 게이트웨이 레벨에서 파라미터를 추가하는 Filter 입니다.<br/>

다음은 AddRequestParameter 를 사용하는 예제입니다.<br/>



OrderApi.java

```java
@RestController
public class OrderApi {  
  private final static Logger logger = LoggerFactory.getLogger(OrderApi.class);
  
  // ...
  
  @GetMapping("/order/statistics/{orderPath}")
  public ResponseEntity<String> statistics(
      @PathVariable("orderPath") String orderPath,
      @RequestParam("region") String region
  ) {
    logger.info("region = {}", region);
    return ResponseEntity.status(HttpStatus.OK).body(orderPath);
  }
    
  // ...

}
```

<br/>



application.yml 은 아래와 같이 변경해줬습니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: order-service-header-filter-example
        uri: http://localhost:8081
        order: 1
        predicates:
		  - Path=/order-service/order/statistics/{orderPath}
        filters:
          - RewritePath=/order-service/(?<path>.*),/$\{path}
          - AddRequestParameter=region, seoul # (1)
# ...
```

(1) 

- `AddRequestParameter=region, seoul` : region 이라는 Request Parameter 를 Gateway 레벨에서 추가해줬습니다.

<br/>

아래의 http 파일을 수행해보면 200 OK 응답을 합니다.

```http
### order-service/order/statistics
GET http://localhost:8080/order-service/order/statistics/card
```

<br/>

WAS 로그를 확인해보면 클라이언트 레벨에서 Request Param 을 내려주지 않았는데도 Gateway 레벨에서 알아서 잘 AddRequestParameter 를 통해 RequestParameter 를 추가해줬음을 확인 가능합니다.

```plain
2024-08-25T01:22:45.893+09:00  INFO 1460724 --- [order-service] [nio-8081-exec-1] i.c.example.order.domain.OrderApi        : region = seoul
```

<br/>



## AddResponseHeader

이 글을 쓰는 저도 아직은 AddResponseHeader 를 언제 써야할지 확신이 잘 안서긴 합니다. 다만 Sanitize 라고 불리는 개념처럼 요청이나 응답의 Header에 잘못된 값이 있을 경우 (일부 누락된 헤더값 등) 보정을 하는 용도로는 유용할 수 있다고 생각하고 있습니다.<br/>

아래 API를 OrderApi 에 추가해주시기 바랍니다.

```java
@RestController
public class OrderApi {
    
    // ...
    
    @GetMapping("/order/download/xls")
    public ResponseEntity<String> downloadXls(
        HttpServletResponse response
    ){
        logger.info("header['Content-Type'] = {}", response.getHeader("Content-Type"));
        return ResponseEntity.status(HttpStatus.OK).body("xls header test");
    }
    
    // ...
    
}
```

<br/>



GatewayService 내의 yaml 파일은 아래와 같이 작성해주시기 바랍니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: order-filter-example
          uri: http://localhost:8081
          order: 1
          predicates:
            - Path=/order-service/order/download/xls
          filters:
            - RewritePath=/order-service/(?<path>.*),/$\{path}
            - AddResponseHeader=Content-Type, application/vnd.openxmlformats-officedocument.wordprocessingml.document
# ...
```

<br/>

http 파일을 아래와 같이 작성한 후 실행해봅니다.

```http
### order-service/order/download/xls
GET http://localhost:8080/order-service/order/download/xls

```

<br/>



출력결과로 Content-Type 이 Resposne Header 로 아래와 같이 찍힌 것을 확인 가능합니다.

```plain
HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 15
Date: Sun, 25 Aug 2024 14:06:18 GMT
Content-Type: application/vnd.openxmlformats-officedocument.wordprocessingml.document

xls header test

Response code: 200 (OK); Time: 16ms (16 ms); Content length: 15 bytes (15 B)

```

<br/>

Gateway 에 의해 추가된 헤더인 `Content-Type: application/vnd.openxmlformats-officedocument.wordprocessingml.document` 을 확인하실 수 있습니다.<br/>

<br/>



## SecureHeaders 

## RewritePath

RewritePath 는 지금까지 예제에서 자주 사용해온 개념입니다.<br/>

GatewayService 내의 application.yml 이 아래와 같이 정의되어 있다고 해보겠습니다.

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: rewrite-path
          uri: http://localhost:8081
          order: 1
          predicates:
            - Path=/order-service/**
          filters:
            - RewritePath=/order-service/(?<path>.*),/$\{path}
# ...
```

<br/>

http 파일에서는 아래의 요청을 보내봅니다.

```http
### order-service
GET http://localhost:8080/order-service/hello
```

<br/>

이렇게 하면 200 OK 의 응답을 얻게 됩니다. 만약 RewritePath 를 사용하지 않는다면 Gateway 인 http://localhost:8080/ 으로 http://localhost:8080/order-service/hello 라는 요청이 왔을 때 Gateway는 http://localhost:8081/order-service/hello 로 요청을 라우팅합니다(요청을 보내줍니다). 이 경우 OrderApi.java 내에 @GetMapping 은 `@GetMapping("/order-service/hello")`으로 작성해야 요청이 매핑될수 있게 됩니다. 이렇게 작성할 경우 Gateway 가 매핑해주는 개별 MSA 내의 RequestMapping 에 Gateway 내의 설정을 따라서 정의해줘야 하기에 Gateway의 설정에 종속되어 자주 바뀌어야 한다는 단점이 있습니다. 즉 최악의 경우 Gateway 설정에 따라 모든 개별 MSA 가 재배포되야 하는 케이스가 생길 수 있다는 단점이 있습니다.<br/>

만약 RewritePath 를 `/order-service/(?<path>.*),/$\{path}` 으로 정의하면 Gateway 인 http://localhost:8080/ 으로 http://localhost:8080/order-service/hello 라는 요청이 왔을 때 Gateway 는 http://localhost:8081/hello 로 요청을 라우팅합니다(요청을 보내줍니다). 이 경우 OrderApi.java 내에 @GetMapping 은 `@GetMapping("/hello")` 로 작성해야 요청이 매핑될 수 있게 됩니다. 이 경우 OrderApi.java 내에 @GetMapping 은 `@GetMapping("/hello")` 으로 작성하면 되기에 Gateway 가 매핑해주는 개별 MSA 내의 RequestMapping을 Gateway 내의 설정을 따라서 작성할 필요가 없습니다. 즉, Gateway 설정이 변경되어도 개별 MSA가 재배포되어야 하는 케이스는 생기지 않습니다.<br/>

<br/>



## RewriteLocationResponseHeader 



## RewriteResponseHeader 



## SetRequestHeader 



## SetStatus 



## RequestSize



# GlobalFilters

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

<br/>



# HttpHeadersFilters

[HttpHeadersFilters](https://cloud.spring.io/spring-cloud-gateway/reference/html/#httpheadersfilters)

- [8.1. Forwarded Headers Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#forwarded-headers-filter)
- [8.2. RemoveHopByHop Headers Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#removehopbyhop-headers-filter)
- [8.3. XForwarded Headers Filter](https://cloud.spring.io/spring-cloud-gateway/reference/html/#xforwarded-headers-filter)

<br/>

