## Item60. 정확한 답이 필요하다면 float 와 double 은 피하라

`float` 과 `double` 은 공학용으로 설계되었다.  넓은 범위의 수를 `근사치` 로 계산하도록 설계되었다. 그리고 `float` 과 `double` 은 이진 부동소숫점 연산을 수행한다. `근사치` 로 계산이 수행되므로, 정확한 결과가 필요할 때는 사용하면 안된다. **`float`, `double` 은 특히 금융관련 계산과는 맞지 않는다.**<br>

이 내용은 자바 퍼즐러 - 2번째 퍼즐, 변화를 위한 시간 에서도 다루는 내용이다. <br/>

<br/>



## 핵심정리

정확한 답이 필요한 계산에는 `float` 나 `double` 을 피하라. 소수점 추적은 시스템에 맡기고, 코딩 시의 불편함이나 성능저하를 신경쓰지 않겠다면 `BigDecimal` 을 사용하라. `BigDecimal` 이 제공하는 여덟가지 반올림 모드를 이용해 반올림을 완벽히 제어할 수 있다. 법으로 정해진 반올림을 수행해야 하는 비즈니스 계산에서 아주 편리한 기능이다.<br>

반면 성능이 중요하고 소수점을 직접 추적할 수 있고 숫자가 너무 크지 않다면 `int` 나 `long` 을 사용하라. 숫자를 아홉자리 십진수로 표현할 수 있다면 `int` 를 사용하고 열여덟 자리 십진수로 표현할 수 있다면 `long` 을 사용하라. 열어덟자리를 넘어가면 `BigDecimal` 을 사용해야 한다.<br>

<br/>



## 근사치를 표현하는 float, double

`float` 과 `double` 은 공학용으로 설계되었다.  넓은 범위의 수를 `근사치` 로 계산하도록 설계되었다. 그리고 `float` 과 `double` 은 이진 부동소숫점 연산을 수행한다. `근사치` 로 계산이 수행되므로, 정확한 결과가 필요할 때는 사용하면 안된다. **`float`, `double` 은 특히 금융관련 계산과는 맞지 않는다.**<br>
<br>



## 예제 1) 간단한 뺄셈 연산

```java
public class Item60Test{
  @Test
  public void 간단한_뺄셈연산(){
    Assertions.assertNotEquals(1.03 - 0.42, 0.61);
    Assertions.assertNotEquals(1.00 - 9 * 0.10, 0.1);
  }
}
```

<br/>



## 예제 2) 달러, 센트 연산

### double, float 을 사용할 경우

가격이 10센트, 20센트.30센트, 40센트로 올라갈때 10+20+30+40 = 100 센트=1달러의 계산을 통해 4개의 아이템을 구매하는 것이 정답이지만, 실제로는 `0.3999999` 라는 값이 나와서 테스트에 실패합니다.

```java
public class Item60Test{
  @Test
  public void 달러_센트_연산(){
    double dollar = 1.00;
    int itemCnt = 0;
    
    for(double price = 0.10; dollar >= price; price += 0.10){
      dollar -= price;
      itemCnt++;
    }

    Assertions.assertNotEquals(itemCnt, 4);
    Assertions.assertNotEquals(dollar, 0);
    System.out.println("잔돈 = " + dollar);
    System.out.println("구입한 개수 = " + itemCnt);
  }
}
```

<br/>



출력결과

```plain
잔돈 = 0.3999999999999999
구입한 개수 = 3
```

<br/>



### 달러,센트 연산을 BigDeciaml 로 계산

가격이 10센트, 20센트.30센트, 40센트로 올라갈때 10+20+30+40 = 100 센트=1달러의 계산을 통해 4개의 아이템을 구매하는 것이 정답이며, BigDecimal 로 연산시에는 정확한 답을 냅니다.

```java
public class Item60Test{
  @Test
  public void 달러_센트_연산을_BigDecimal로_계산(){
    final BigDecimal TEN_CENTS = BigDecimal.valueOf(0.10);

    int itemCnt = 0;
    BigDecimal dollar = BigDecimal.valueOf(1.00);
    for(BigDecimal price = TEN_CENTS; dollar.compareTo(price) >= 0; price = price.add(TEN_CENTS)){
      dollar = dollar.subtract(price);
      itemCnt++;
    }

    Assertions.assertEquals(itemCnt, 4);
    Assertions.assertTrue(dollar.equals(BigDecimal.valueOf(0.00)));
    System.out.println("잔돈 = " + dollar);
    System.out.println("구입한 개수 = " + itemCnt);
  }
}
```

<br/>



출력결과

```plain
잔돈 = 0.0
구입한 개수 = 4
```

<br/>



### 3) 정수형으로 치환 후 계산

이 경우 정수형 치환에 대한 기준이 정확하지 않으며 자주 변한다면 장애로까지 이어질 수 있는 방식입니다. 소수 연산을 정수형으로까지 치환하려기 보다는 가급적이면 BigDecimal 을 사용하는 방식을 더 권장합니다.<br/>

```java
public class Item60Test{
  @Test
  public void 정수_단위로_환산해서_계산(){
    int itemCnt = 0;
    int dollar = 100;
    for(int cent = 10; dollar >= cent; cent+=10){
      dollar -= cent;
      itemCnt++;
    }

    Assertions.assertEquals(itemCnt, 4);
    Assertions.assertTrue(dollar == 0);
    System.out.println("잔돈 = " + dollar);
    System.out.println("구입한 개수 = " + itemCnt);
  }
}
```

<br/>



출력결과

```plain
잔돈 = 0.0
구입한 개수 = 4
```



