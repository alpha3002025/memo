### String 리터럴과 new String 의 차이점



### 참고자료

- [What is String Pool? - journaldev.com](https://www.digitalocean.com/community/tutorials/what-is-java-string-pool)

<br/>



### new String() 과 리터럴("")의 차이점

**""(리터럴)** 방식은 **Heap 내부의 Constant Pool 영역**에 저장되며, 이미 만들어진 문자열이 있다면 새로 문자열 객체를 만들지 않고 Constant Pool 영역에 이미 저장되어 있는 것을 재할용합니다. Constant Pool 은 Heap 영역에 위치해있습니다.<br/>

**new String()** 방식은 **Heap 영역에 문자열을 새로 만들어서 저장**한다. 생성시마다 새로운 객체를 생성합니다.

<br/>

![1](https://journaldev.nyc3.cdn.digitaloceanspaces.com/2012/11/String-Pool-Java1-450x249.png)

출처 : 

- [What is String Pool? - journaldev.com](https://www.digitalocean.com/community/tutorials/what-is-java-string-pool)
- https://www.digitalocean.com/community/tutorials/what-is-java-string-pool 

<br/>



### String 클래스의 intern() 메서드

String 클래스에는 intern() 이라는 메서드가 있습니다. 

intern() 메서드는 만약 같은 값을 가진 **String 객체가 이미 String Constant Pool 내에 존재하면 이미 존재하는 해당 객체를 그대로 리턴**해줍니다.<br/> 

만약 같은 값을 가진 **String 객체가 String Constant Pool 내에 존재하지 않으면 새로 문자열 객체를 생성**해서 String Constant Pool 에 저장하고 생성한 문자열 객체의 reference 를 리턴합니다.<br/>

<br/>



**e.g.**

```java
@Test
void intern_메서드_테스트(){
    String str1 = "안녕하세요";
    String str2 = new String("안녕하세요");
    assertThat(str1).isNotSameAs(str2);
    System.out.println("str1 == str2 ? " + (str1 == str2));

    assertThat(str1).isSameAs(str2.intern());
    System.out.println("str1 == str2 ? " + (str1 == str2.intern()));
}
```

<br/>



**출력결과**

```plain
str1 == str2 ? false
str1 == str2 ? true

Process finished with exit code 0
```

<br/>



### 불변(Immutable)방식으로 String 을 사용할 경우 의 장점

- Thread Safe

- 보안

<br/>



**Thread Safe**<br/>

String 객체를 불변으로 제공하면, 여러 쓰레드에서 어떤 특정 String 객체를 동시에 접근해도 안전합니다.<br/>

<br/>



**보안**<br/>

중요한 데이터를 문자열로 다루는 경우 현재 참조하고 있는 문자열 값을 변경해서 이 변경된 문자열 값을 다른 스레드에서의 관련된 로직에서 읽어들이면서 같은 문자열이 재활용되면서 원하지 않는 값이 야기되는 사이드 이펙트가 발생할 수 있는데 불변으로 String 을 사용할 경우 이런 이슈에서 자유롭게 됩니다.<br/>

<br/>




