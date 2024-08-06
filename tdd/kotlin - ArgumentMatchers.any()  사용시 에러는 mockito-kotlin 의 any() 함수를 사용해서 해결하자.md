### kotlin - ArgumentMatchers.any()  사용시 에러는 mockito-kotlin 의 any() 함수를 사용해서 해결하자

mockito 의 ArgumentMatchers 를 사용해서 발생하는 문제입니다. non-null 관련 에러와 관련있습니다.

mockito 대신 mockito-kotlin 라이브러리의 ArugumentMatchers를 사용해야 합니다.

<br/>



### 참고자료

- [Mockito and non-null types in Kotlin](https://www.damirscorner.com/blog/posts/20221223-MockitoAndNonNullTypesInKotlin.html)
- [github.com/DamirsCorner/20221223-kt-mockito](https://github.com/DamirsCorner/20221223-kt-mockito)
- 검색어(구글) : mockito kotlin any type
- [mvnrepository - mockito-kotlin](https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin)

<br/>



### mockito 의 ArgumentMatchers.any() 를 사용할 때의 에러

먼저 mockito 의 ArgumentMatchers.any() 를 사용할 때의 에러입니다.<br/>

아래 코드에서 (1) 로 표시한 부분에서 에러가 납니다.<br/>

```kotlin
@WebMvcTest
@ExtendWith(MockitoExtension::class)
class SignupMockMvcTest {
  private lateinit var mockMvc: MockMvc
  
  // ... @MockBean 들 모음
  
  @BeforeEach
  fun init(){
    // mockMvc 초기화
  }
  
  @Test
  fun `회원가입 (Normal)`(){
    // (1)
    Mockito
    .`when`(userApplicationService.signup(ArgumentMatchers.any()))
    .thenReturn(SignupResponse("asdf", "asdf@gmail.com", "encoded"))

    // ... 중략 ...
    
  }
  
}
```

<br/>



에러 메시지의 일부는 아래와 같습니다.

```plain
org.mockito.exceptions.misusing.InvalidUseOfMatchersException: 
Misplaced or misused argument matcher detected here:

-> at net.spring.cloud.prototype.userservice.application.rest.mockmvc.SignupMockMvcTest.회원가입 (Normal)(SignupMockMvcTest.kt:84)

You cannot use argument matchers outside of verification or stubbing.
Examples of correct usage of argument matchers:
    when(mock.get(anyInt())).thenReturn(null);
    doThrow(new RuntimeException()).when(mock).someVoidMethod(any());
    verify(mock).someMethod(contains("foo"))
    
This message may appear after an NullPointerException if the last matcher is returning an object 
like any() but the stubbed method signature expect a primitive argument, in this case,
use primitive alternatives.
    when(mock.get(any())); // bad use, will raise NPE
    when(mock.get(anyInt())); // correct usage use

Also, this error might show up because you use argument matchers with methods that cannot be mocked.
Following methods *cannot* be stubbed/verified: final/private/equals()/hashCode().
Mocking methods declared on non-public parent classes is not supported.
```

<br/>



mockito 의 ArgumentMatchers 를 사용하는 것으로 인한 에러인데, 이 문제는 mockito-kotlin 을 사용해서 해결 가능합니다. <br/>

<br/>



### mockito-kotlin 의 any() 를 사용

[의존성](https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin) 추가

```kotlin
testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
```

<br/>



위에서 사용한 코드는 아래와 같이 수정해줬습니다.

```kotlin
import org.mockito.kotlin.any // (2)

// ...

@WebMvcTest
@ExtendWith(MockitoExtension::class)
class SignupMockMvcTest {
  private lateinit var mockMvc: MockMvc
  
  // ... @MockBean 들 모음
  
  @BeforeEach
  fun init(){
    // mockMvc 초기화
  }
  
  @Test
  fun `회원가입 (Normal)`(){
    // (1)
    Mockito
    .`when`(userApplicationService.signup(any()))	// (1)
    .thenReturn(SignupResponse("asdf", "asdf@gmail.com", "encoded"))

    // ... 중략 ...
    
  }
  
}
```



- (1) 
  - `org.mockito.kotlin` 의 any() 함수를 사용했습니다.
- (2)
  - `org.mockito.kotlin.any()` 함수를 import 했습니다.

<br/>



