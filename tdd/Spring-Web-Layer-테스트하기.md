# 스프링 Web Layer 테스트하기

<br/>

## 참고자료

- Testing the Web Layer
  - [https://spring.io/guides/gs/testing-web/](https://spring.io/guides/gs/testing-web/)
- [[docs.spring.io] MockMvcBuilders](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/setup/MockMvcBuilders.html)
- [[docs.spring.io] AbstractMockMvcBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/setup/AbstractMockMvcBuilder.html#build--)
- [[docs.spring.io] StandaloneMockMvcBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/setup/StandaloneMockMvcBuilder.html)
- [[docs.spring.io] MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
- [[docs.spring.io] ResultActions](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html)

<br/>



## 테스트 시 웹 레이어를 구동시키는 세가지 방법들

- `@SpringBootTest` 
- `@AutoConfigureMockMvc` 
- `@WebMvcTest` 

<br/>



### @SpringBootTest

스프링 애플리케이션 컨텍스트를 구동해서 테스트를 하는 방식입니다.<br/>

@SpringBootTest 어노테이션을 사용하면 메인 Configuration 클래스(예를 들면 @SpringBootApplication 과 함게 선언된)를 참조하고, 이 설정 파일을 이용해서 스프링 애플리케이션 컨텍스트를 구동시킵니다.<br/>

아래는 @SpringBootTest 어노테이션을 사용해 테스트를 구동하는 예제입니다. 스프링 부트 애플리케이션을 구동시켜서 테스트한다. 즉, 임베디드 톰캣을 직접 구동시켜서 하므로 어느정도는 무거운 테스트 방식입니다.<br/>



```java
package com.example.testingweb;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Hello, World");
	}
}
```

- `webEnvironment = WebEnvironment.RANDOM_PORT`
  - 테스트를 위한 서버를 랜덤한 포트로 구동시킵니다. (테스트 환경이 충돌날수도 있는 것을 피할 수있는 좋은 방법)

- `@LocalServerPort`
  - 할당받은 Server 포트를 멤버필드에 저장해두기 위해 사용한 어노테이션입니다.


<br/>



### @AutoConfigureMockMvc

`@AutoConfigureMockMvc` 어노테이션을 사용하면 서버를 구동시키지 않으면서, 서버가 동작하는 아래의 계층(레이어)만 테스트하는 방식입니다.<br/>

`@AutoConfigureMockMvc` 어노테이션을 사용하면 실제 HTTP 요청을 처리할 때와 동일한 방식으로 코드가 호출되지만 서버 시작 비용은 들지 않습니다. 그리고 거의 모든 스택이 사용됩니다.<br/>

`@AutoConfigureMockMvc` 어노테이션은 Spring 의 MockMvc를 사용하고, 테스트 케이스에서 `@AutoConfigureMockMvc` 를 사용합니다.<br/>

서버를 구동시는 것은 아니지만, 스프링 애플리케이션의 거의 모든 컨텍스트가 시작됩니다.<br/>

```java
package com.example.testingweb;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World")));
	}
}
```

<br/>



### @WebMvcTest

@AutoConfigureMockMvc 는 서버를 구동시는 것은 아니지만, 스프링 애플리케이션의 거의 모든 컨텍스트가 시작됩니다. 하지만, web layer 만에 한정해서 테스트의 범위를 줄일 수 있는 경우 역시 있습니다.<br/>

@WebMvcTest 어노테이션을 사용하면 테스트 범위를 웹 레이어에 한정하도록 축소해서 테스트할 수 있습니다.<br/>

또는 특정 컨트롤러에 한정해서만 @WebMvcTest를 사용할 수 있습니다. <br/>

<br/>



e.g. @WebMvcTest(HomeController.class) 

```java
@WebMvcTest
public class WebLayerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World")));
	}
}
```

컨트롤러 계층에 다른 컴포넌트를 선언해두어서 의존성이 있을 경우 @MockBean 어노테이션을 사용해서 GreetingService 타입의 mock 객체를 생성하고 주입할 수 있습니다. <br/>



## MockMvc 테스트

MVC 계층에서 사용되는 객체를 Mocking해서 테스트하는 방식입니다.<br/>

`MockMvc` 객체는 스프링 MVC 계층을 Mocking 할 수 있도록 스프링에서 제공해주는 클래스입니다. 그리고 이 `MockMvc` 객체는 `perform()` , `andDo()` , `andExpect()` 의 동작을 수행할수 있도록 메서드를 제공해주고 있습니다. 이 메서드 각각은 `ResultHandlers` , `ResultMatcher` 인스턴스 각각의 동작을 받아 서로 다른 동작을 할 수 있도록 하는 역할들을 수행합니다.<br/>

`MockMvcBuilders` , `andDo()` , `andExpect()` , `ResultHandlers` , `ResultMatcher` 를 사용하는 실제 예를 보면, 잘 짜여진 API 인것 같다는 생각이 듭니다.<br/>

MockMvc 객체를 테스트할 때 일반적으로 자주 거치는 순서/단계는 아래와 같습니다.

- MockMvc 객체 생성하기
  - MockMvcBuilders 를 이용해 생성합니다.
- MockMvc 객체로 perform 
- MockMvc 객체로 perform 에 대해 expect 선언문 작성하기
  - perform 에 대해 기대되는 결괏값은 여러 가지가 있을 수 있는데, 이것에 대해 정의하는 과정입니다.
- perform 과 expect 이전에 해야 하는 동작을 andDo 로 선언하기

<br/>



### MockMvc 객체 생성하기

> 참고 : 
>
> - [[docs.spring.io] MockMvcBuilders](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/setup/MockMvcBuilders.html)
> - [[docs.spring.io] AbstractMockMvcBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/setup/AbstractMockMvcBuilder.html#build--)

<br/>

MockMvc 객체를 생성할 때, 보통 MockMvcBuilders 를 이용해 생성합니다.<br/>

MockMvcBuilders 클래스를 이용해 MockMvc 객체를 만들때 Mocking 된 웹 애플리케이션의 동작 범위를 `@Controller` 타입의 인스턴스 들을 직접 지정해서 해당 범위에서만 테스트할 지, 또는 완전하게 갖춰진 `WebApplicationContext` 를 사용해서  전체 웹 애플리케이션 범위에서 테스트할 지를 선택할 수 있습니다.<br/>

- standaloneSetup(Object... controllers)
  - 테스트하려는 `@Controller` 타입의 인스턴스들을 standaloneSetup 에 등록할 수 있습니다.
- webAppContextSetup(WebApplicationContext context)
  - 테스트할 수 있는 범위를 WebApplicationContext 전역으로 지정합니다. 
  - 웹 계층에 등록된 모든 컴포넌트들에 MockMvc로 접근할 수 있게 되는데, 이 때 접근하는 객체는 Mocking 된 객체입니다.
- build()
  - AbstractMockMvcBuilder 클래스의 메서드입니다.
  - MockMvc 객체를 생성합니다. (빌더패턴)

<br/>

> 참고) MockMvcBuilders 클래스 만을 이용해 MockMvc 객체를 생성할 수 있는 것은 아닙니다. 사용자가 직접 MockMvcBuilders를 커스터마이징한 클래스로 MockMvc 객체를 생성하는 것 역시 가능합니다.

```java
@WebMvcTest // SpringBootTest 역시 가능하다.
public class EmployeeControllerTest {
  @Autowired
  private EmployeeController controller;
  
  private MockMvc mockMvc;
  
  @BeforeEach
  void setup(){
    mockMvc = MockMvcBuilders
        // MockMvc 인스턴스는 보통 MockMvcBuilders 클래스의 standaloneSetup(Controller) 메서드를 사용한다.
        // 인자값으로 사용되는 controller 는 Spring 컨테이너 내에 존재하는 Controller 인스턴스다.
        .standaloneSetup(controller)
        // MockMvc 객체를 최종적으로 반환해주는 것은 build() 메서드다.
        .build();
  }
}
```



### 테스트 실행하기

> 참고)
>
> - [[docs.spring.io] MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
> - [[docs.spring.io] ResultActions](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html)
> - perform() : ResultActions -> andDo(ResultHandler), andExpect(ResultMatcher), andReturn()

<br/>



