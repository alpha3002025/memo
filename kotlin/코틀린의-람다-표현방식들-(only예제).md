## 코틀린의 람다 표현방식들 (only 예제)



## 람다식이란?

코틀린의 람다식은 java 와는 조금 다른 방식으로 표현됩니다.

e.g. 

- 아래는 코틀린에서의 람다 표현식입니다.

```kotlin
{result, op -> result + op}
```

<br>

e.g. 위의 코틀린 람다식은 Java 람다식으로 표현 시에 아래와 같이 변환가능합니다.

```java
(result, op) -> result + op
```

<br>



## e.g. 1) `{객체명 -> 원하는 코드}`

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun getUsers() : List<UserResponse> {
        return userRepository.findAll()
        	.map { user -> UserResponse(user) }
    }
    
}
```

<br/>

이 코드를 java 로 표현하면 아래와 같습니다.

```java
@RequiredArgsConstructor
@Service
class UserService{
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
        return userRepository.findAll()
            .map(user -> new UserResponse(user))
            .collect(Collectors.toList());
    }
}
```

<br/>



## e.g. 2\) it 키워드 - `{ someMethod(it)}` 

kotlin 은 map() 등과 같은 잘 알려진 함수형 메서드 내에서 `it` 라는 변수로 해당 스코프 내에서의 자기자신을 가리키는 객체를 표현 가능합니다.

```kotlin
@Service
class UserService(
    private val userREpository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun getUsers() : List<UserResponse> {
        return userRepository.findAll()
        	.map{ UserResponse(it) }
    }
    
}
```

<br/>



## e.g. 3\) 메서드 레퍼런스

(1)

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun getUsers() : List<UserResponse> {
        return userRepository.findAll()
        	.map(::UserResponse)
    }
    
}
```

<br/>



(2)

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun updateUserName(request: UserUpdateRequest){
        val user = userRepository
        	.findById(request.id)
        	.orElseThrow(::IllegalArgumentException)
        
        user.updateName(request.name)
    }
    
}
```

<br/>



## e.g. 4\) 인라인 람다

sum 이라는 함수를 직접 선언하는 것이 아닌 람다 식으로 정의하는 예제를 살펴봅시다.

```kotlin
@Test
fun lambda_test1(){
    val sum = { x1: Int, x2: Int -> // (1)
                   println("x1 = $x1 , x2 = $x2")
                   println("now sum two int number")
                   x1 + x2 // (2)
              }

    assertThat(sum(1,2)).isEqualTo(3)
}
```

(1)

-  `{x:Int, y:Int -> x1+x2}` 으로 람다식을 선언했습니다.

(2) 

- 코틀린에서는 람다 내에서 return 문을 사용하지 않고 return 하려는 값이나 객체를 마지막 라인에 둡니다.
- (저 역시 처음 배울때 가장 멘붕 오는 문법이었던 것 같습니다.)

<br/>



## e.g. 5\) 함수의 마지막 인자가 람다일때

아래와 같은 함수가 잇다고 해보겠습니다. 자세히 보면함수의 마지막 인자가 람다식입니다.

```kotlin
fun operation(x1: Int, x2: Int, expr: (Int, Int) -> Int): Int{
    return expr(x1, x2)
}
```

<br/>



가장 마지막 인자가 expr 이라는 인자이고 람다타입입니다.<br/>

operation 함수를 호출하는 경우를 살펴볼까요?<br/>

```kotlin
@Test
fun lambda_test2(){
    val result : Int = operation(1,2){x1, x2 -> x1*x2}
    assertThat(result).isEqualTo(2)
}
```

직접 호출할 때에는 마지막 인자가 람다인 함수는 람다 바디를 함수의 마지막 인자로 두는 것도 가능하지만 보통은 일반적으로 위와 같이 함수의 맨 뒤에 `{... }` 으로 감싸서 람다를 인자값으로 전달합니다. 대부분의 코틀린 내부 라이브러리들이 위와 같이 선언되어 있기도 합니다.<br/>

자주 쓰이는 표현이기에 익숙해지셔야 하는 표현식입니다.<br/>

<br/>



## it : 람다를 위한 간이 구문

코틀린은 파라미터가 단 하나 뿐인 람다를 편하게 쓸 수 있는 간이 구문을 제공합니다. 간이 구문에서는 유일한 파라미터의 이름을 `it` 로 부릅니다.<br>

e.g.

```kotlin
fun triple(list: List<Int>): List<Int> = list.map {it*3}
```

위와 같이 한줄로도 작성할 수 있지만, 람다가 여러개 있을 때 내포된 람다가 있을 때 it 가 가리키는 대상을 추측하기 어려워질 수 있습니다.<br>

따라서 아래 코드들 처럼 람다를 여러줄에 걸쳐서 쓰는 것이 좋은 습관입니다.<br>

<br/>



### `it` 파라미터로 람다를 정의

```kotlin
fun triple(list: List<Int>): List<Int> = list.map{
    it*3
}
```

<br/>



### 일반 람다로 표현

```kotlin
fun triple(list: List<Int>): List<Int> = list.map{ a ->
	a * 3
}
```

<br/>



## SAM 변환

내부적으로 람다를 익명의 객체로 변환할 때 SAM 변환이라는 것을 거칩니다.

- Single Abstract Method 변환
- SAM 변환은 자바에서 작성한 인터페이스일 경우에만 동작합니다.
- 코틀린에서는 인터페이스 대신에 함수를 사용합니다.



자바에서는 메서드를 하나만 가진 이터페이스를 구현할 때 람다기반의 함수를 구현합니다.<br>

코틀린에서는 추상 메서드를 하나를 인수로 사용할 때 람다처럼 함수를 인자로 전달할 수 있습니다.<br>

<br/>



**1) 인터페이스 타입을 인라인으로 구현**

button 객체에 대한 click 이벤트 리스너를 구현하는 코드

```kotlin
button.setOnClickListener(object: View.OnClickListener{
    override fun onClick(v: View?){
        // 클릭 시 처리
    }
})
```

<br>



**2) 인터페이스 타입을 람다로 구현**

위의 코드는 아래와 같이 수정할 수 있습니다.

```kotlin
button.setOnClickListener({v: View? -> 
	// 클릭시의 동작을 정의
})
```

<br>



**3) 자료형을 생략한 람다식으로 구현**

인자값의 자료형 추론이 가능하면 아래 처럼 자료형을 생략한 람다식으로 정의할 수 있다.

```kotlin
button.setOnClickListener{v -> 
	// 클릭 시 처리
}
```

<br>



**4) `it` 를 인수로 해서 구현**<br>

`it` 를 인수로 해서 사용하는 것도 가능하다.<br>

람다식에서 인수가 하나인 경우에는 인수를 아예 생략하고 람다 블록 내에서 인수를 `it` 로 접근할 수 있다.<br>

아래 코드에서 `it` 는 `View?`  타입의 인수 v 를 의미한다.

```kotlin
button.setOnClickListener {
    it.visibility = View.GONE
}
```

<br>



## e.g. 6\) 일반 함수를 람다식으로 전달하기

e.g. 아래와 같은 코드가 있다고 해보겠습니다.

```kotlin
fun sum (numbers: IntArray) = 
	aggregate(numbers, fun(result, op) = result + op)
```

<br/>



이 코드는 아래와 같이 함수 부분을 람다로 전달 가능합니다.

```kotlin
fun sum (numbers: IntArra) = 
	aggregate(numbers, {result, op -> result + op})
```

<br/>



이 것들을 Function Value, Functional Value 라고 부르는데, 저는 사실 이 용어가 무슨뜻인지 아직도 감이 안잡히고 그냥 위와 같이 쓰일수 있다는 건 알것 같습니다.<br/>

<br/>

