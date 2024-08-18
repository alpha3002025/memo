## 압축필터 사용

이번 문서는 별 내용이 없긴 하다.

```yaml
server:
  compression:
    enabled: true
```

이렇게 지정하면 resposne 가 gzip 으로 전달된다.<br/>

Gateway 를 BFF 로 여러개 띄워서 사용한다면 web 이 아닌 mobile 에만 gzip 으로 전달되게끔 하는 등으로 헤더 분류를 바꿔주는 것도 가능하다.<br/>

<br/>



