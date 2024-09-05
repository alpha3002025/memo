## 코틀린의 reduce(), fold()

e.g. reduce()

```kotlin
val r1 = list1.reduce { total, num -> total + num }

println("r1 = $r1")

// 출력결과
r1 = 6
```

<br/>



e.g. fold(Int)

fold(Int) 함수 역시 누적값을 계산하기 위해 사용됩니다. reduce() 와 다른 점은 fold(Int) 내에 전달되는 인자값은 누적 계산을 시작할 초기값을 의미합니다.<br/>

```kotlin
val list2 = listOf(1,2,3,4,5)

val r3 = list2.fold(10){total, num -> total + num} 
// 1+2+3+4+5 = 15 and + 10 = 25

val r4 = list2.fold(1){total, num -> total * num}
// 1*2*3*4*5 = 120

val r5 = list2.fold(10){total, num -> total * num}
// 1*2*3*4*5 = 120 and * 10 = 120*10 = 1200

println("r3 = $r3")
println("r4 = $r4")
println("r5 = $r5")

// 출력결과
r3 = 25
r4 = 120
r5 = 1200
```

<br/>



e.g. fold(Int)

- 멀티 라인으로 정의해보기

```kotlin
val r22 = list1.fold(0){ total, num ->
	println("total = $total , num = $num")
	total + num
}

println("rr2 = $r22")

// 출력결과
total = 0 , num = 1
total = 1 , num = 2
total = 3 , num = 3
rr2 = 6
```

