## Gateway 실제 사용사례 수집



## toss

참고 : [toss 는 Gateway 이렇게 씁니다.](https://toss.tech/article/slash23-server)

Reactive Web 기반으로 Gateway 를 사용<br/>

필터 개발에 Kotlin Coroutine 을 적극 활용<br/>

Istio 의 ingress/egress Gateway, Envoy 필터 역시 함께 사용<br/>

- Sanitize
  - 잘못된 요청의 경우 잘못된 요청을 지워서 올바른 값으로 바꿔주는 기능
  - 사용자가 악의적으로 값을 주고 요청하더라도 Gateway 에서 올바른 값으로 바꿔서 서비스에 넘겨줌
- User Passport
  - 이전에는 사용자 정보가 필요할 때 User API를 호출하는 방식으로 유저 정보를 가져왔었지만, 트랜잭션 내에 불필요한 중복요청을 유발하며 서버 리소스의 낭비로 이어짐
  - 이것을 개선하기 위해 Netflix 의 Passport 구조를 참고해 토스에 맞도록 토스 Passport 를 구현
  - Netflix 는 유저 인증시에 Passport 라는 id 토큰을 트랜잭션 내로 전파하는 방법ㅇ르 사용하고 있는데 이 구조를 토스 유저 서비스 상황에 맞게끔 토스 Passport 로 구현
  - Passport 란? 
    - 사용자 기기 정보와 유저 정보를 담은 하나의 토큰
    - 앱에서 user 식별 키와 함께 API 를 요청하게 되면 Gateway 에서 이 키를 토대로 인증 서버에 Passport 를 요청
    - Passport 에는 디바이스 정보, user 정보가 담겨있으며 Gateway 는 이것을 Serialize 해서 서비스에 전파
    - user 정보가 필요할 경우 user 정보 호출 없이 passport 를 통해 user 에 대한 정보를 사용
- 보안
  - endpoint 간 암호화
    - 앱에서 서버와 통신시 API를 사용하게 되는데 이때 App, API 두 엔드포인트간 암호화를 통해 패킷 분석을 어렵게 만들어서 정보를 안전하게 전달
    - 앱에서 Gateway 로 요청 전송시 암호화 키를 이용해 요청 바디를 암호화해서 전송
    - Gateway 에서는 이것을 복호화 해서 서비스로 넘겨준다.
    - 복호화 과정에서는 인증/인가 로직이 수행되며 복호화된 데이터, 유저 정보를 서비스에 전달
    - Gateway 에서 이 과정을 모두 처리하므로 개별 서비스는 인증에 대한 내용에 대해 관심을 가질 필요없이 사용자의 요청을 처리하는 데에만 집중
  - Dynamic Security
    - 요청이 발생한 곳이 위변조를 가하지 않은 토스 앱인지도 검증을 한다.
    - 토스앱은 내부적으로 매 요청에 대해 아주 짧은 유효기간을 가진 안전한 키 값과 함께 변조되지 않은 토스 앱에서만 알수 있는 정보를 이용해서 각 요청을 서명하고 Gateway 로 보낸다. (솔트 값을 자주 바꿔서 키값을 안전하게 유지하고, 변조되지 않은 토스 앱에서만 파악가능한 정보들도 함께 파악해서 변조 여부를 탐지)
    - FDS(Fraud Detection System) 을 따로 구축해둠
    - 이렇게 해서 변조를 가하지 않은 토스 앱에서 만들어진 요청인지, 중복된 것인지, 키가 유효기간이 만료되었는지를 검증하는 작업을 통해 앱 위변조, delayed request, replaying attack 을 방지. 
  - 인증서를 이용한 인증/인가
    - 외부회사, 내부 개발자 서비스 호출시에 인증서를 사용
    - istio 위에 Gateway 를 두었고, Gateway 에서 인증/인가를 처리하도록 적용
    - istio 만을 이용해 인증/인가를 처리할 수 있지만, Gateway 와 같은 코드 기반의 애플리케이션 레벨에서의 인증 인가가 istio 의 matching rule 보다 자유도가 높으며 auditing 을 처리할 수 있고, 카나리 배포의 이점을 누릴 수 잇기 때문에 Gateway 에서 인증/인가 처리를 담당하도록 함
    - Istio 는 인증서의 CA 유효성을 확인하는 역할을 수행하며 그 인증서의 정보를 헤더에 실어서 Gateway 에 전달
    - 이렇게 받은 인증서를 decode 해서 X.509  extensions 중 Subject Alternative Name 을 활용해서 인증서로부터 사용자 정보를 획득
    - 게이트웨이는 사용자 정보, 도착지 호스트, 요청경로 등을 통해 각 요청에 대해 인증/인가/Auditing 을 수행
    - ![](https://static.toss.im/ipd-tcs/toss_core/live/70a44aba-41e7-467d-93be-bccdbe6e7f37/slash23_%EC%B5%9C%EC%A4%80%EC%9A%B0_28.png)

- Circuit Breaker
  - Circuit Breaker 는 istio 를 이용한 하드웨어(인프라)레벨의 서킷 브레이커 방식이 잇고, Resilience4j 또는 Hystrix 같은 소프트웨어 레벨의 서킷브레이커 방식이 있는데, 토스에서는 소프트웨어 레벨의 서킷브레이커 방식을 채택
  - 즉, Resilience4j, Hystrix 기반의 서킷브레이커를 사용
  - Istio 를 사용하면 호스트 단위로 쉽고 빠르게 전체 적용이 가능하고 개발주기에 의존하지 않고 독립적으로 관리할 수 있겠지만, Istio 는 호스트 단위로만 서킷 브레이킹이 설정이 가능하며 설정할 수 있는 룰도 많지 않다는 단점이 있어서 소프트웨어 레벨의 서킷브레이커인 Resilience4j 를 선택
- 모니터링
  - Gateway 를 지나는 모든 요청, 응답의 Route id, method, URI, 상태 코드들을 Elasticsearch 에 남기고 있다. 따라서 요청이 어떤 Route 로 들어왔는지 업스트림으로 어떤 URI를 호출했는지에 대한 정보를 바로 확인하는 것이 가능
- 메트릭
  - 성능정보
  - 시스템에서 수집하는 메트릭, 애플리케이션에서 수집하는 메트릭이렇게 두 종류가 있는데, 두 메트릭 모두 Prometheus 에서 수집
  - 시스템 메트릭은 Node Export 로 수집
  - 애플리케이션 메트릭은 Spring Actuator 로 수집
  - 이 메트릭은 Grafana 로 시각화, 슬랙으로 알람 전송
  - 시스템 메트릭: CPU, memory, 네트워크 RX, TX 트래픽
  - 애플리케이션 메트릭 : JVM thread block 상황, 세대별 메모리 할당, Full GC 발생 여부 확인
  - ![](https://static.toss.im/ipd-tcs/toss_core/live/a88b9fe4-b029-4abf-8644-a0a548ea2120/slash23_%EC%B5%9C%EC%A4%80%EC%9A%B0_57.png)
- Spring Cloud Gateway 를 사용하면  Route 별로 메트릭을 수집할 수도 있다. 여기에 Path 값도 함께 해서 API Path 별 Route 메트릭을 확인하기도 한다.







