# kubeadm upgrade

북마크에 있는 도큐먼트만 골고루 보고 북마크 위치 익숙해지는 연습을 자주 하자.<br/>

시간이 얼마 많지 않다고 느껴도 한달에 20일 매일 + 주말 1시간\~ 2시간 꾸준히 하면 한달, 두달 뒤에는 뭐가 되있을 것이기에 그냥 계속 해나가면 된다!! 라고만 생각하고 묵묵히 계속 하고, 급해지는 것만 하지 말자. 그 과정에는 뭐가 되있긴 할 것이기에 자꾸 요령 찾지 말고 하다보면 요령 찾아지니 일단 하는 것부터!!<br/>

<br/>



# 도큐먼트

- GettingStarted / Production environment / Installing Kubernetes with deployment tools / Bootstrapping clusters with kubeadm / [Installing kubeadm](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/)

- Tasks / Administer a Cluster / Administration with kubeadm / [Upgrading kubeadm clusters](https://kubernetes.io/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/)
  - 북마크 : [Trobuleshooting / kubeadm 클러스터 업그레이드](https://kubernetes.io/ko/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/)
  - '업그레이드 할 버전' 챕터 
    - en : [Determine which version to upgrade to](https://kubernetes.io/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#determine-which-version-to-upgrade-to)
    - ko : [kubeadm 클러스터 업그레이드#업그레이드할 버전 결정](https://kubernetes.io/ko/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C%ED%95%A0-%EB%B2%84%EC%A0%84-%EA%B2%B0%EC%A0%95)

<br/>



# kubernetes 클러스터 업그레이드 

kubernetes 클러스터 내에서 사용되는 kubeadm, kubelet, kubectl 을 업그레이드 하는 것을 kubernetes 업그레이드라고 부른다.

- kubeadm : 클러스터를 부트스트랩하는 명령을 수행하는 역할
- kubelet : Pod, Container 시작 등의 작업을 수행
- kubectl : 클러스터 접근/제어를 위한 CLI 유틸



업그레이드 절차를 요약해보면 이렇다.

- Upgrade 할 master 노드에 ssh 접속
- 업그레이드 할 버전 확인
- kubeadm 업그레이드
- 노드 드레인 (마스터 노드 또는 외부 접속 클라이언트에서 kubectl 로 실행)
- kubelet, kubectl 업그레이드
- 노드 uncordon



<br/>



# 업그레이드 할 버전 결정

ubuntu

```bash
apt update
apt-cache madison kubeadm
# 목록에서 최신 버전(1.31)을 찾는다
# 1.31.x-00과 같아야 한다. 여기서 x는 최신 패치이다.
```

<br/>



CentOS, RHEL 또는 Fedora

```bash
yum list --showduplicates kubeadm --disableexcludes=kubernetes
# 목록에서 최신 버전(1.31)을 찾는다
# 1.31.x-0과 같아야 한다. 여기서 x는 최신 패치이다.
```

<br/>



# 마스터 노드로 ssh 접속

```bash
## 현재 컨텍스트 확인
$ kubectl config current-context


## 클러스터 내의 모든 노드들의 리스트 확인
$ kubectl get nodes
NAME			STATUS			ROLES			AGE			VERSION
hk8s-m		...
hk8s-w1		...
hk8s-w2		...


## hk8s 로 ssh 접속 (hk8s-m 은 /etc/hosts 내에 등록한 별칭)
$ ssh hk8s-m 
```

<br/>



# 업그레이드할 버전 선택

설치 가능한 버전 확인 Documentation

- en : [Determine which version to upgrade to](https://kubernetes.io/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#determine-which-version-to-upgrade-to)
- ko : [kubeadm 클러스터 업그레이드#업그레이드할 버전 결정](https://kubernetes.io/ko/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C%ED%95%A0-%EB%B2%84%EC%A0%84-%EA%B2%B0%EC%A0%95)

```bash
## OS 버전 확인
$ sudo cat /etc/os-release
...
centos


## 위의 도큐먼트에서 제공하는 명령어로 설치 가능한 버전을 선택 
## 이번 예제에서는 Centos 이기에 위의 도큐먼트에서 제공하는 CentOS, RHEL 명령어를 선택
$ sudo yum list --showduplicates kubeadm --disableexcludes=kubernetes
...
kubeadm.x84_64 			1.31.3-0		kubernetes
## 이 중 가장 마지막 버전인 1.31.3-0 을 선택


```

<br/>



# 컨트롤 플레인 노드 업그레이드

컨트롤 플레인 노드 업그레이드 Documentation

- 같은 문서 내 Upgrade Control plain nodes 에 있는 문서
- en : https://kubernetes.io/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#upgrading-control-plane-nodes

위의 문서에서 제공하는 Centos, Fedora 버전으로 절차를 요약해보면 다음과 같다. 위의 문서에서 Ubuntu 역시 제공하고 있는데 Ubuntu 버전은 위의 문서를 참고하자.

```bash
## install
$ sudo yum install -y kubeadm-1.31.3-0 --disableexcludes=kubernetes

## version 확인
$ kubeadm version

## 업그레이드 플랜 확인
## 현재 노드 상태에서 v1.31.0 으로 업그레이드 가능한지 마스터노드 내에서 확인하는 절차 
## (kubeadm 이 검사해서 현재 노드내의 상황을 체크해서 설치가 가능한 여건인지 체크하는데, 노드가 정상적인 상태라면 대부분 설치할 수 있다는 문구가 나타난다.)
$ kubeadm upgrade plan

## 설치 시작
$ sudo kubeadm upgrade apply v1.31.0

## 컨트롤 플레인을 2개 또는 3개로 구성하는 고 가용성 환경이라면 다음과 같이 다른 컨트롤 플레인에 대해 다음의 작업들을 수행한다.

```







