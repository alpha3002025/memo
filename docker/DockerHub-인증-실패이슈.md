 Docker Hub에서는 Pro 계정이라도 **Personal Access Token (PAT)**을 사용하는 것을 권장합니다. 일반
  비밀번호 대신 PAT를 생성해서 사용해야 합니다.

⏺ 문제 원인 및 해결 방법

  Kafka 설치가 멈춘 이유를 파악했습니다:

  🔍 발견된 문제들

  1. Primary Issue: Docker Hub 인증 실패
    - 401 Unauthorized 및 429 Too Many Requests 에러
    - "too many failed login attempts for username or IP address"
    - Kafka, Zookeeper 등 모든 컨테이너가 ImagePullBackOff 상태
  2. Secondary Issue: imagePullSecrets 누락
    - install-kafka.sh:37, install-kafka.sh:124
    - install-redis.sh, install-mysql.sh
    - local-mongodb-deployment.yaml

  ✅ 완료한 작업

  모든 설치 스크립트에 imagePullSecrets 추가:
  - ✓ install-kafka.sh (Zookeeper & Kafka StatefulSet)
  - ✓ install-redis.sh (Helm chart)
  - ✓ install-mysql.sh (Helm chart)
  - ✓ local-mongodb-deployment.yaml (Deployment & Jobs)

  🚨 해결이 필요한 핵심 문제

  Docker Hub 인증 실패가 근본 원인입니다. 다음 단계를 수행해야 합니다:

  1. Docker Hub Personal Access Token (PAT) 생성:
    - https://hub.docker.com/settings/security 접속
    - "New Access Token" 클릭
    - Token 이름 입력 (예: "kubernetes-cluster")
    - Access permissions: "Read-only" 선택
    - Token 생성 및 복사 (한 번만 표시됨!)
  2. 환경변수로 Docker Hub 인증정보 설정:
  export DOCKER_USERNAME="<username>"
  export DOCKER_PASSWORD="<생성한-PAT>"
  export DOCKER_EMAIL="<your-email>"
  3. 설치 재실행:
  source local-install-infra-and-app.sh test-20251025-1

  현재 비밀번호로는 "too many failed login attempts" 상태여서 일시적으로 차단되어 있습니다. PAT를
  사용하면 이 문제가 해결됩니다.