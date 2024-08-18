## Spring Boot 구동중인 포트구하기

## 참고자료

- https://www.baeldung.com/spring-boot-running-port

<br/>



## 설명

여러가지 방법들이 있지만, 내 경우에는 Runtime 에 현재 WAS가 어느 포트로 열려있는지 확인해야 했다. server.port = 0 으로 세팅해서 구동중인 WAS가 구동되고 있는 포트를 알아야 했기 때문이다.<br/>

<br/>



## ServletWebServerWebApplicationContext 

방법은 간단하다. 만약 Servlet 버전의 web(spring-boot-starter-web)을 사용중이라면 WebServerWebApplicationContext 를 주입받아서 아래와 같이 사용하면 된다.

```java
@RequiredArgsConstructor
@RestController
public class WebSocketController {
    // ...
    
    private final ServletWebServerApplicationContext serverApplicationContext;
    
    // ...
    
    @MessageMapping("/incoming")
    @SendTo("/topic/outgoing")
    public String incoming(Message message){
        int port = serverApplicationContext.getWebServer().getPort();
        log.info(String.format("메세지 수신 : %s", message));
        return String.format("서버 port = %s, 메시지 = %s", port, message.message());
    }
}
```

<br/>



## ReactiveWebServerWebApplicationContext

만약 Reactive 버전의 Web(spring-boot-starter-webflux)을 사용중이라면 ReactiveWebServerWebApplicationContext 를 주입받아서 사용하면 된다.<br/>

코드는 생략하기로 햇다 하하하하ㅏ하하하하ㅏ하.<br/>

