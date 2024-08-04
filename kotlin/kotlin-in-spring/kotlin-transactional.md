스프링 기반의 코틀린 애플리케이션 내에서 스프링의 트랜잭션 기능 사용시 아래와 같은 에러를 낼수 있습니다.

```plain
Methods annotated with '@Transactional' must be overridable
Make 'saveUser' 'open', Make 'UserService' 'open'
```

<br/>



## 1\) open 키워드 적용

```kotlin
@Service
// open 키워드를 붙였다.
open class UserService(
    private val userRepository: UserRepository,
){
    // open 키워드를 붙였다.
    @Transactional
    open fun saveUser(request: UserCreateRequest){
        
    }
}
```

@Transactional 을 사용하면, 스프링에서는 내부적으로 @Transactional 을 적용한 클래스를 상속받은 가짜객체인 트랜잭션 프록시 객체를 생성하고 해당 메서드를 overriding 합니다.<br>

그런데 코틀린에서는 클래스를 상속받을 수 있게 하려면 `open` 이라는 키워드를 사용해야 합니다. 그래서 위의 코드에서는 `open` 키워드를 붙였습니다.<br/>

<br>



## 2\) jetbrains 에서 제공하는 spring 플러그인 적용

위와 같이 코드를 적용하는 것 말고 jetbrains 에서 제공하는 spring 플러그인을 적용해서도 해결이 가능합니다.

```kotlin
plugins {
    id "org.jetbrains.kotlin.plugin.spring" version "1.6.21"
}
```

