# 예전의 자료 조사 기억

예전에 ELK 대신 Loki 를 도입하는 것에 대해 검색해본 적이 있다.<br/>

결론을 내려보면 이랬다.<br/>

- ELK 대신 Loki 를 도입하려는 곳들도 많지만 결론적으로는 Loki 는 AWS Fargate 와 호환안되는게 있어서 아직은 도입을 못하는 곳들이 더 많다

- ELK 보다 성능/비용은 Loki 가 더 효율적이라고 함

- ELK 특유의 인덱싱 부하가 심한 문제로 인해.. 많은 곳에서 도입을 꺼리고 있다.

<br/>



인덱스 하나에 몰아두는 방식으로 하면 아마도 인덱싱에 문제가 생길듯 한데 아마도 대부분의 회사들은 인덱스 하나에 몰아두는 방식으로 해서 인덱싱에 문제가 생기는가 보다 싶다는 생각을 했었다.<br/>

<br/>



# 이번 스터디에서는 ELK 대신 Loki

이번 디렉터리에서는 ELK는 배제하고, Grafana 와 Loki 를 어떻게 활용할수 있는지 그 방법들을 정리해보기로 했다. 회사 입장에서도 ELK 를 사용하는 것에 비해 비용이 줄어든다면 더 장점이 될 수 있겠다는 생각을 했다.<br/>

그리고 이제는 로그 처리에 특화된 ELK 보다는 메트릭데이터에 특화된 Prometheus, Loki 를 고려해보는 것이 어느 정도는 장점이 될 수 있겠다 싶다. Fargate 와 호환이 안되는 문제 등에 대해 논의 되는 부분들이 있는데 이 부분에 대해서는 직접 대안들을 찾아나가봐야 할 것 같다.<br/>

<br/>



# 참고 용어들

## Promtail

- Logback 으로 남긴 로그들을 외부에서 가져갈 수 있도록 해줘야 하는데 Promtail 프로세스를 구동시키면 설정시 작성한 config.yml 내의 clients 에 명시한 monitoring server 로 각각의 개별 job 들에 명시해둔 파일들을 Monitoring server 로 전송한다.
- 주로 Loki 서버로 전송한다.

## Loki

- Loki 는 Log 를 위한 DBMS다. (Distributor, Ingester 등 여러 컴포넌트 들의 집하베)
- Loki 는 전통적인 데이터베이스들과는 다르다.
- 로그 특성에 맞게 시계열 데이터 저장에 특화되어 있다.
  - 로그 외에도 Label, Timestamp 를 통한 인덱싱 데이터 역시 함께 저장한다.
  - 데이터는 DynamoDB를 사용할수도 있고 FileSystem 을 사용하는 것도 가능하다.
- PromQL 을 통해 데이터를 질의하며, Grafana 에서도 PromQL 을 통해 데이터를 커스텀하는 것이 가능하다.
- 대량의 로그 처리를 위해 트랜잭션 및 일관성을 포기하는 대신 성능을 취한다.

<br/>



예를 들면 아래와 같은 화면을 확인 가능하다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FrK3id%2FbtsqZa4GLO8%2FwPalBfMM2mjDcipmXp7Pw1%2Fimg.png)

이미지 출처 : https://jaehee329.tistory.com/45

<br/>



# 참고자료

이번 스터디를 위해 자료조사를 위해 찾아봤던 자료들은 아래와 같다.

구글 검색어
- loki 로그
- grafana spring boot

<br/>



Loki 사용하는 것이 좋은지 적합성 관련 자료

- [다양한 로그 수집 서비스 비교 분석 (ELK, Fluentd, Datadog, Rollbar, Sentry)](https://velog.io/@thankspotato/%EB%8B%A4%EC%96%91%ED%95%9C-%EB%A1%9C%EA%B7%B8-%EC%88%98%EC%A7%91%EA%B8%B0-%EB%B9%84%EA%B5%90-%EB%B6%84%EC%84%9D-ELK-Fluentd-Datadog-Sentry)

<br/>



Loki

- [Spring Boot Application 과 Grafana 기반의 Metric & Log 모니터링](https://jaehee329.tistory.com/45)
- [Promtail, Loki 를 사용한 Logback 모니터링](https://velog.io/@roycewon/Promtail-Loki%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%9C-Logback-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81)

- [Logging/Grafana Loki 를 통한 로그 수집하기](https://medium.com/@dudwls96/logging-grafana-loki-%ED%86%B5%ED%95%9C-%EB%A1%9C%EA%B7%B8-%EC%88%98%EC%A7%91%ED%95%98%EA%B8%B0-d57ba1b75ab3)
- [모니터링링링 (prometheus, grafana, promtail, loki)](https://velog.io/@junsj119/%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81-%EA%B4%80%EB%A0%A8)
- [Sring Boot 모니터링 적용](https://jujeol-jujeol.github.io/2021/10/28/Spring-Boot-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81-%EC%A0%81%EC%9A%A9-2/)

<br/>



기타 자료들

- https://devocean.sk.com/blog/techBoardDetail.do?ID=163964
- [https://velog.io/@flaehdan/Grafana-Loki-%EB%A1%9C%EA%B7%B8-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81](https://velog.io/@flaehdan/Grafana-Loki-로그-모니터링)
- https://creampuffy.tistory.com/213
- https://nyyang.tistory.com/192
- [https://velog.io/@choihuk/Grafana-Loki-Stack%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%B5%AC%EC%B6%95-%EB%A1%9C%EA%B7%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-2](https://velog.io/@choihuk/Grafana-Loki-Stack으로-로그-시스템-구축-로그-시스템-2)
- https://inma.tistory.com/164

<br/>



# 개인적으로 공부 중인 ELK

개인적으로 공부중인 elk 는 CKA 준비를 위해 잠시 중단해둔 상태인데, 연휴 때 마다 주제를 정해서 몰아서 듣기로 결정했다. 주로 상품 검색, 오탈자 보정에 특화된 스터디를 할 것 같다.<br/>

<br/>







