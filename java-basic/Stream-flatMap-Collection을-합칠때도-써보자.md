## Stream flatMap - Collection을 합칠때도 써보자

두개의 List 를 합쳐야 하는 일이 있었다. 보통 백엔드 개발의 특성상 리스트를 합치기보다 쿼리를 Union 하시는 분들도 가끔 있다.<br/>



나는 Union 보다는 Java 로직을 선택했다. 나중에 필터링하는 로직이나, Java 객체들 병합하는 로직들은 테스트코드로 만들어두기에도 좋은 편이라고 생각해서다.<br/>

나는 처음에는 Collection 의 addAll 을 생각했었다. 그런데 이건 너무 나이브한 생각이었다. 그래서 이것 저것 찾아보다보니 flatMap 이 있었다. WebFlux나 코틀린을 써볼때 flatMap 을 그렇게 많이 써왔으면서 왜 Java 를 쓰면서 flatMap 을 떠올리지 못한걸까<br/>

좀 당황하긴 했었다.<br/>

오늘 그래서 풀었던 건 대충 이런 모습이었다.

```java
// 스테이지 선물 상품들 조회
List<ProductStatus> stageGiftList = productRepository.findStageGift();

// 일반 구매 상품 조회
List<ProductStatus> purchaseProductList = productRepository.purchaseProduct();

// 여기서 이렇게 풀었다.
List<ProductStatus> merged = Stream
    .of(stageGiftList, purchaseProductList)
    .flatMap(Collection::stream)
    // 별도의 비즈니스로직들
    .collect(Collectors.toList());
```

<br/>



flatMap 말고도 Stream의 concat을 통해 서로 다른 Stream을 열어서 붙이는 방법도 있고 그 외에도 Guava 의 여러 라이브러리를 사용하는 방식들도 있다.<br/>

<br/>



## 참고

- [Combine Multiple Collections](https://www.baeldung.com/java-combine-multiple-collections)



