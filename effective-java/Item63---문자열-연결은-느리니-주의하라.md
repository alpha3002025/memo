## Item 63. 문자열 연결은 느리니 주의하라

문자열 연결이 많아질 수록 성능저하를 감내하기 어렵습니다. 문자열은 불변(아이템 17)이어서 두 문자열을 연결할 경우 양쪽의 내용을 모두 복사해야 합니다. 이로 인해 문자열 연결 연산자(`+`)로 문자열 n개를 잇는 시간은 n^2 에 비례하게 됩니다.<br/>

문자열 연결연산자(`+`) 대신 `StringBuilder` 의 `append` 메서드를 사용하라. 문자 배열을 사용하거나, 문자열을 (연결하지 않고) 하나씩 처리하는 방법도 있습니다.<br/>

자바 6 이후로 문자열 연결 성능을 다방면으로 개선했지만, `StringBuilder` 를 사용하는 것과 문자열 연결 연산자 `+` 를 사용하는 것의 성능 차이는 여전히 큽니다.<br/>

<br/>



## 성능측정

문자열 연결을 StringBuilder, String의 + 연산, concat 등으로 테스트해보며 성능을 측정한 문서가 있습니다. 아래 문서를 확인해주세요

- [java-basic/문자열-특징정리--문자열-결합하기-및-성능측정.md](https://github.com/chagchagchag/memo/blob/main/java-basic/%EB%AC%B8%EC%9E%90%EC%97%B4-%ED%8A%B9%EC%A7%95%EC%A0%95%EB%A6%AC--%EB%AC%B8%EC%9E%90%EC%97%B4-%EA%B2%B0%ED%95%A9%ED%95%98%EA%B8%B0-%EB%B0%8F-%EC%84%B1%EB%8A%A5%EC%B8%A1%EC%A0%95.md)

<br/>



## 핵심정리

성능에 신경써야 한다면 많은 문자열을 연결할 대는 문자열 연결자(`+`)를 피하자. 대신 `StringBuilder` 의 `append` 메서드를 사용하라. 문자 배열을 사용하거나, 문자열을 (연결하지 않고) 하나씩 처리하는 방법도 있다.<br/>
<br/>



## 문자열 연결 연산자(+)의 단점
문자열 연결 연산자(`+`) 는 여러 문자열을 하나로 합쳐줄 수 있는 편리한 수단입니다. 한줄 또는 작고 크기가 고정된 문자열 표현을 만들때라면 크게 문제가 되지 않습니다.<br/>

하지만, 문자열 연결이 많아질 수록 성능저하를 감내하기 어렵습니다. 문자열은 불변(아이템 17)이어서 두 문자열을 연결할 경우 양쪽의 내용을 모두 복사해야 합니다. 이로 인해 문자열 연결 연산자(`+`)로 문자열 n개를 잇는 시간은 n^2 에 비례하게 됩니다.<br/>

e.g. 아래는 문자열 연결을 사용하는 예다.

```java
public String statement(){
    String result = "";
    for(int i=0; i<numItems(); i++){
        result += lineForItem(i); // 문자열 연결
    }
    return result;
}
```

<br/>

위 코드에서 `numItems()` 로 가져오는 아이템의 수가 많다면, 성능 저하를 유발할 수 있습니다. 따라서 `String` 대신 `StringBuilder` 를 사용해야 합니다.<br/>
<br/>



## StringBuilder 를 사용해 개선하기

```java
public String statement2(){
    StringBuilder b = new StringBuilder(numItems() * LINE_WIDTH);
    for(int i=0; i<numItems(); i++){
        b.append(lineForItem(i));
    }
    return b.toString();
}
```

