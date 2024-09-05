## 코틀린의 ::class

class 타입을 import 할때 java 와는 다르기에 처음에는 혼동이 옵니다.<br/>

java 는 `.class` 를 사용하지만 kotlin 은 `::class` 를 사용합니다.<br/>

다음은 MockitoExtension 을 ExtendWith 하는 코드입니다.

```java
@ExtendWith(MockitoExtension.class)
class SomeTest{
    
}
```

<br/>



코틀린에서는  `::class` 키워드를 사용하면 됩니다.

```kotlin
@ExtendWith(MockitoExtension::class)
class SomeTest{
    
}
```

