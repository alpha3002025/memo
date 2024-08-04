# 코틀린의 if, switch, when, for, while 루프

# if ~ else 문

코틀린에서는 신기하게도 if \~ else 구문 마저도 식이고 변수에 할당이 가능합니다.

```kotlin
val price : Int = 33000

val str = if(price > 33000){
    "안돼... 사지마!!!"
} else {
    "사든가 말든가... 알아서 해"
}

println(str)
println()
```

<br>

출력결과

```plain
사든가 말든가... 알아서 해
```

<br>



# 삼항연산자

코틀린에는 삼항연산자가 없습니다. if \~ else 자체가 표현식이기에 불필요합니다.

```kotlin
val a = 1
val b = 2

val c = if (b > a) b else a
```

<br>



# when (switch \~ case 문법)

switch \~ case 문법과 유사한 역할을 수행한다.

```kotlin
val number : Int = 3
val when1 = when(number) {
    1 -> "one"
    2 -> "two"
    3 -> "three"
    4 -> "four"
    5 -> "five"
    else -> "whatever"
}

println("when1 = $when1")
println()
```

<br>

출력결과

```plain
when1 = three
```

<br>



이번에는 함수에서 when 절을 사용해봅니다.<br/>

```kotlin
fun main(){
    println("one_two_three(3) = ${one_two_three(3)}")
}

fun one_two_three(number: Int) : String{
    return when(number){
        1 -> "one"
        2 -> "two"
        3 -> "three"
        else -> "??"
    }
}
```

<br>

출력결과

```plain
one_two_three(3) = three
```

<br>



이번에는 enum 을 when 절에서 사용해봅니다.

먼저 enum으로 사용할 타입은 아래와 같습니다.

```kotlin
enum class StockMarketType{
    NASDAQ, KOSPI, KOSDAQ
}
```

<br>



조건문에 여러개의 조건을 걸 수도 있습니다.<br>

```kotlin
fun main(){
    when(getMarketType(StockMarketType.NASDAQ.name)){
        StockMarketType.NASDAQ -> println("미국주식")
        StockMarketType.KOSPI, StockMarketType.KOSDAQ -> println("한국주식")
    }
}

fun getMarketType(name: String) : StockMarketType {
    return StockMarketType.valueOf(name)
}
```

<br>



출력결과

```plain
미국주식
```

else 를 사용하지 않아도 컴파일 에러가 나지 않는 경우도 있습니다.<br>

나머지 경우의 수가 없다는 것이 컴파일 타임내에 추론이 가능하다면 else 를 사용하지 않아도 됩니다.<br>

예를 들면 아래와 같은 경우가 있습니다.<br>

<br>

```kotlin
enum class OneTwoThree{
    ONE, TWO, THREE
}

fun main(){
    when(OneTwoThree.ONE){
        OneTwoThree.ONE -> "하나"
        OneTwoThree.TWO -> "둘"
        OneTwoThree.THREE -> "셋"
    }
    println()
}
```

<br>



# for loop

자바의 `foreach` 와 유사한 문법입니다.<br>

범위 연산자 `..`,  연산자 `until` , `step` 연산자, `downTo` 등의 연산자들이 있습니다.<br>

<br>



## 범위연산자 `..`

범위연산자 `..` 을 사용해 for loop 을 돌릴 수 있습니다.<br>

<br>

```kotlin
fun main(){
    for(number in 1..7){
        println(number)
    }
}
```

<br>

출력결과

```plain
1
2
3
4
5
6
7
```

<br>

## `until` 연산자

until 연산자를 사용해서 `<` 연산을 수행할 수 있습니다.

```kotlin
fun main(){
    for(number in 1 until 7){
        println(number)
    }
    println()
}
```

<br>

출력결과

```plain
1
2
3
4
5
6
```

<br>

## `step` 연산자

step 만큼 증가시켜가면서 for loop 연산을 수행합니다.

```kotlin
fun main(){
    for(number in 1..7 step 2){
        println(number)
    }
    println()
}
```

<br/>

출력결과

```plain
1
3
5
7
```

<br/>



## `downTo` 연산자

`downTo` 를 사용해서 얼마까지 감소시킬지를 명시한 후 for loop 내에서 값을 감소시키면서 for 문을 순회하는 것 역시 가능하다.

```kotlin
fun main(){
    for(number in 7 downTo 2){
        println(number)
    }
    println()
}
```

<br>



출력결과

```plain
7
6
5
4
3
2
```

<br>



## 배열 반복하기

배열을 for 문에서 반복하는 코드는 아래와 같다.

```kotlin
fun main(){
    val numbers = arrayOf(1,2,3,4,5)
    
    for(n in numbers){
        println(n)
    }
    println()
}
```

<br>



출력결과 

```kotlin
1
2
3
4
5
```

<br>



# while

자바의 while 문과 같은 문법을 가지고 있다.<br>

예제는 생략