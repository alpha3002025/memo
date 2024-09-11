# Item55. 옵셔널 반환은 신중히 하라

값을 반환하지 못할 가능성이 있고, 호출할 때 마다 반환값이 있을 가능성을 염두에 둬야 하는 메서드라면 옵셔널을 반환해야 할 상황일 수 있다. 하지만 옵셔널 반환에는 성능저하가 뒤따르니, 성능에 민감한 메서드라면 `null` 을 반환하거나 예외를 던지는 편이 나을 수 있다. 그리고 **옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.**<br/>
<br/>



# null vs Optional (Java 8 이전, Java 8 이후)

Java 8 이전

- 예외 던진다.
- `null` 을 리턴한다.
  - 별도의 null 처리 코드가 기하급수적으로 늘어난다는 것이 단점

Java 8 이후

- `Optional<T>` 라는 선택지
- 옵셔널은 원소를 최대 1 개 가질수 있는 `불변` 컬렉션 (장점이다.)
- 예외를 던지는 메서드보다 유연하다. 사용하기 쉽다.



<br/>



# Optional.empty(), Optional.of(), Optional.ofNullable()

옵셔널을 반환하는 메서드에서 `null` 값을 명시적으로 던지지 말자.

- Optional.empty()
  - 빈값을 처리할 때 사용. 비어 있는 옵셔널
- Optional.of()
  - 값이 들어있는 옵셔널. 널 값은 허용하지 않는 메서드
  - 만약, Optional.of()에 null 값을 넘기면, `NullPointerException` 을 던진다.
- Optional.ofNullable()
  - null 값도 허용하는 옵셔널을 만들 때 사용



<br/>



# 스트림의 종단 연산들 중 상당수는 옵셔널을 반환한다.

```java
public static <E extends Comparable<E>> Optional<E> max (Comparable<E> c){
  return c.stream().max(Comparator.naturalOrder());
}
```

<br/>



# 옵셔널을 반환해야 하는 경우에 대한 선택기준

- 옵셔널은 검사예외(CheckedException)와 취지가 비슷하다.
- `Optional.empty` 일 수 있음을 명확히 한다는 것이 장점이다.

<br/>



어떤 함수에서 비검사예외(UnCheckedException)를 throw 하거나, null을 리턴하는 경우

- 처리가 쉽지 않고 예상치 못한 결과를 낼 수 있다.

반면, 검사예외를 throw 하면

- 클라이언트 측에서 대응 코드를 작성하게 된다.



<br/>

이번 챕터에서는 저자는 비검사예외(UncheckeExcpetion) 과 검사예외 (CheckedException)의 차이를 비교하면서 Optional 이 검사예외(CheckedException) 처럼 클라이언트 측에서 대응 코드를 작성하게 한다는 점에서 Optional 을 사용하는 것이 장점을 가지고 있다고 이야기한다.<br/>

검사예외 (CheckedException) 은 굉장히 신중하게 throw 해야 한다. 하지만 Optional 은 검사예외 (CheckedException) 만큼 신중하게 사용하지 않아도 된다. 즉, 검사예외 (CheckedException) 만큼 신중하게 사용하지 않아도 된다. 즉, 검사 예외 (CheckedException) 보다 Optional 이 더 편리하다.<br/>



책에서는 이렇게 비검사예외와 검사예외의 차이를 비교하면서 Optional 이 검사예외처럼 클라이언트 측에서 대응코드를 작성하게 한다는 점에서 Optional을 사용하는 것의 장점을 언급하고 있다. <br/>

참고로, 검사 예외는 굉장히 신중하게 throw 해야 한다. 하지만 Optional 은 검사예외 만큼 신중하게 사용하지 않아도 된다. 즉, 검사예외보다는 편리하다.(위의 내용 까지는 책에서 언급하는 비유였는데, 뺄지 말지 고민을 하다가, 빼지를 못하겠어서 정리는 해두었다. 개인적으로는 굳이 검사 예외로까지 비교할 필요는 없다는 생각을 했었다.)<br/>
<br/>

값이 없을 수 있음을 `NullPointerException` 없이 명확하게 표현해준다는 점을 떠올려보면 장점이 꽤 명확하다.<br/>
예를 들면, 값이 비어있을 수도 있는 메서드를 호출해서 반환값을 처리할 때 아래와 같은 경우 들로 처리하는 것이 가능해진다.<br/>
<br/>
e.g. 1\)

```java
public void orderSomething(){
  Optional<String> menu = getMenu();
  
  menu.ifPresent(m -> {
    orderService.order(m);
  });
}
```

<br/>

또는 아래와 같이 처리하는 것도 가능하다.<br/>
<br/>

e.g. 2\)

```java
public void orderSomething(){
  Optional<String> menu = getMenu();
  if(menu.isEmpty()) return;
  
  orderService.order(m);
}
```

<br/>





