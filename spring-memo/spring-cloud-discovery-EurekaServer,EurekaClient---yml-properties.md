## Eureka Server, Client - yml properties



## 참고자료

- [Eureka-Server/Eureka-Client](https://velog.io/@sodliersung/Eureka-Server-Eureka-Client#2-eureka-client)

<br/>





## Eureka Server

eureka.clent

- eureka.client.register-with-eureka: false
- eureka.client.fetch-registry: false

eureka.server

- eureka.server.enable-self-preservation: true
  - 일시적인 네트워크 장애 같은 상황으로 인한 서비스 장애를 막기 위한 설정입니다.
  - 활성화 되었을 경우 Heart Beat 이 오지 않을 경우에도 그 인스턴스를 정상으로 간주하고 유지합니다.
  - 디폴트 값으로 활성화 되어 있어서 특정 서비스가 일시적으로 멈췄을 때 그 인스턴스를 유지하는 데에 도움이 됩니다.
  - 운영환경에서는 가급적 이 속성을 true 로 유지하는 것을 권장합니다.
  - 즉, 특정 eureka client 를 종료한다고 해서 Eureka Server 에서 바로 해제되지는 않습니다.
  - 만약 false 로 설정한다고 해도 문제가 있는 서비스가 바로 해제되지는 않습니다.
  - client 의 lease-renewal-interval-in-seconds 값을 기본값으로 두면 30초 간격으로 client 가 server 로 heart beat 을 전송합니다.
  - lease-renewal-interval-in-seconds 설정으로 인해 Heart Beat 을 받지 못하면 기본 설정 시 90 초 이후에 서비스 등록이 해지되기 때문입니다.
- eureka.server.response-cache-update-interval-ms: 4000
  - Eureka 서버의 캐싱 업데이트 주기입니다.
- eureka.server.eviction-interval-timer-in-ms: 7000
  - 클라이언트로부터의 Heart Beat 점검 주기를 설정하는 옵션입니다.

<br/>



## Eureka Client

eureka.client.register-with-eureka, eureka.client.fetch-registry 는 많이 쓰는 속성이라서 30초만 자세히 보고 왜 필요한지 이해한 후 외우려 하면 외워지긴 하고, 나머지는 문서화가 필요해보여서 문서화를 시작합니다.<br/>

- eureka.client.register-with-eureka: true
  - Eureka 서버에 자기 자신을 등록할지에 대한 설정
- eureka.client.fetch-registry: true
  - Eureka 서버의 registry (Eureka 서버 내에 등록된 서비스 목록)을 가져올지에 대한 여부
  - 활성화 하면 30초에 한 번씩 Eureka Client 가 Eureka 서버 내의 레지스트리 변경 사항 여부를 재확인합니다.
  - 검색 시 마다 Eureka Server 를 호출하는 대신 레지스트리가 로컬에 캐싱됩니다.

- eureka.client.registry-fetch-interval-seconds: 3
  - 서비스 레지스트리 목록을 캐싱하는 주기를 설정합니다.
- eureka.client.disable-delta: false
  - 서비스 레지스트리 목록을 캐싱할 때 변경된 부분(delta)만 업데이트할지에 대한 설정입니다.
  - default 는 false 입니다.
  - 위의 설정은 true 로 지정했는데, true 로 설정하면 변경된 부분만 가져오기에 불필요한 네트워크 트래픽을 줄일 수 있습니다.
- eureka.client.lease-renewal-interval-in-seconds: 30
  - Eureka Server 로 Heart Beat 을 보내는 시간 주기를 설정합니다. 
  - default 는 30 초 입니다.
  - Eureka Server 의 enable-self-preservation 을 비활성화 해둔 상태일 경우 eureka.client.lease-renewal-interval-in-seconds 을 조절하면 서비스 레지스트리 목록에서 문제있는 서비스들을 목록에서 제거하는 시간을 조절할 수 있게 됩니다.
  - 내부 로직에 영향을 끼칠 수 있기에 가급적이면 기본값 그대로 두는 것을 권장합니다.
- eureka.client.lease-expiration-duration-in-seconds: 10
  - 마지막 hearbeat 이후로 서비스 해제되기 전까지 걸리는 시간을 설정합니다.
  - 내부 로직에 영향을 끼칠 수 있기에 가급적이면 기본값 그대로 두는 것을 권장합니다.
  - lease-renewal-interval-in-seconds 보다 값이 커야 합니다.



