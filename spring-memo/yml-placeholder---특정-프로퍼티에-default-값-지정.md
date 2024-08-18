## yml placeholder - 특정 프로퍼티에 default 값 지정

예를 들어 아래와 같은 속성을 application.yml 에 지정했다고 하자.

```yaml
# ...

broker:
  relay:
    host: ${BROKER_HOST:localhost}
```

<br/>



이 경우 jar 파일을 구동시킬 때  `BROKER_HOST` 를 환경 변수로 넘겨주지 않으면 `broker.relay.host` 에 대해 기본값으로 `localhost` 를 지정하겠다는 의미다.<br/>

<br/>





