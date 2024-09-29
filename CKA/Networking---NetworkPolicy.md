# Networking - NetworkPolicy

Pod 로 트래픽이 들어오고(Inbound) 나가는(Outbound) 정책을 의미

- Ingress 트래픽 : Inbound 정책
- Egress 트래픽 : Outbound 정책

<br/>



트래픽 컨트롤 정의하는 방식들

- ipBlock : 특정 ip 대역 (ipBlock) 에 대해 정의하는 방식 (CIDR IP 대역)
- podSelector : label 을 이용해서 특정 label 이 적용된 pod 들만을 선택할 수 있도록 하는 방식 
- namespaceSelector : 특정 namespace 로부터 들어오는 트래픽을 받을수 있도록 지정하는 방식(e.g. beta 애플리케이션은 beta namespaceSelector 를 통해 Network Traffic Policy 가 적용되도록 지정)
- Protocol & Port : 특정 protocol, 특정 port 에 대한 트래픽만 받을 수 있도록 지정하는 방식



공식문서

- https://kubernetes.io/docs/concepts/services-networking/network-policies/#networkpolicy-resource



# e.g. 1

- namespace: prod, 레이블은 purpose=production 이 붙어있는 Pod 들만 ingress (inbound) 허용
- namsepace: dev, 레이블은 purpose=development 가 붙어있는 Pod 들만 ingress (inbound) 차단

<br/>



참고)

- k8s 는 CNI로 플라넬을 사용한다.
- hk8s 는 CNI 로 칼리코를 사용한다.

NetworkPolicy 는 CNI 가 Network Policy 를 지원해야 사용가능하다. 플라넬은 NetworkPolicy 를 지원하지 않는다. 즉, k8s 에서는 NetworkPolicy 를 적용 불가능하며, 시험에는 hk8s 에 대해서만 NetworkPolicy 를 적용하는 문제가 출제될 것이라고 추측해볼 수 있다.



## 풀이

```bash
# hk8s 로 스위칭
$ kubectl config use-context hk8s

# pod 들을 조회한다. -A 옵션을 붙여서 조회한다.
# hk8s 클러스터이면서 calico 가 붙어있는 것을 확인했다.
$ kubectl get pod -A
NAMESPACE				NAME													READY				STATUS ...
kube-system			calico-kube-controllers-...		1/1					Running
kube-system			calico-node-...								1/1					Running
kube-system			calico-node-...								1/1					Running
kube-system			calico-node-...								1/1					Running
...

# nginx 이미지를 기반으로 80 포트를 리슨하는 webpod 라는 pod 을 --dry-run=client 로 돌려보고 이상이 없는지 확인한다.
$ kubectl run webpod --image=nginx --port=80 --labels=app=web --dry-run=client -o yaml
...


# 위의 CLI가 정상적으로 동작하는 것을 확인했으므로 --dry-run=client 를 빼고 실행시켜본다.
$ kubectl run webpod --image=nginx --port=80 --labels=app-web
pod/webpod created
...


# webpod 의 상태를 확인한다.
$ kubectl get pod -o wide webpod
NAME			READY		STATUS								RESTARTS		IP ...
webpod		0/1			ContainerCreating			0						<none>

... 

조금 기다렸다가 다시 실행해보면 Running 으로 변하게 된다.
$ kubectl get pod -o wide webpod
NAME			READY		STATUS								RESTARTS		IP 		...
webpod		0/1			Running								0						192.168.234.194


namsepace, ip 주소, label 을 복사해둔다.
default
webpod: 192.168.234.194
app: web


# 문제에서 이야기하고 있는 namespace 들을 생성한다.
$ kubectl create namespace dev
namespace/dev created
$ kubectl create namespace prod
namespace/prod created


# 각 namespace 들에 label 을 붙여준다.
$ kubectl label namespace prod purpose=production
namespace/prod labeled
$ kubectl label namespace dev purpose=devlopment
namespace/dev labeled


# Label이 제대로 붙었는지 확인
$ kubectl get namespaces -L purpose
NAME					STATUS			...				PURPOSE
...
dev						Active								development
prod					Active								production
...

# 또는 아래와 같이 확인하는 것 역시 가능하다. 다만 -L 옵션이 더 보기 깔끔하게 나타난다.
$ kubectl get namespaces --show-labels 


# 북마크 내 NetworkPolicies/NetworkPolicies - ingress_egress 를 방문한다.
# https://kubernetes.io/docs/concepts/services-networking/network-policies/#networkpolicy-resource
# 문서 내에서 The NetworkPolicy resource 챕터에 있는 yaml 파일 내용 중 아래의 내용을 선택해서 복사한다.
# 그리고 아래의 내용을 web-allow-prod.yaml 이라는 이름의 파일로 저장한다. (web-allow-prod 라는 이름의 리소스를 생성할 것이기에 web-allow-prod 라는 이름으로 생성했다.)
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: web-allow-prod # web-allow-prod
  namespace: default
spec:
  podSelector:
    matchLabels:
      app: web	# app=web 이라는 label 을 적용
  policyTypes:
  - Ingress # Ingress Rule 만 적용할 것이기에 Egress Rule 은 삭제하고 Ingress Rule 만 남겨둔다.
  ingress:
  - from:
  	# ipBlock, podSelector 는 필요가 없기에 지워준다.
    - namespaceSelector:
        matchLabels:
          purpose: production # purpose=production 이라는 label 을 추가해줬다.
    ports:
    - protocol: TCP
      port: 80 # 80 포트에 대한 Rule 을 적용할 것이기에 80 을 지정해줬다.



# 클러스터에 web-allow-prod 라는 NetworkPolicy 적용
$ kubectl apply -f web-allow-prod.yaml
networkpolicy.networking.k8s.io/web-allow-prod created
...


# 확인
$ kubectl get networkpolicies
NAME								POD-SELECTOR			AGE
web-allow-prod			app=web
...

# 자세하게 확인
$ kubectl describe networkpolices
Name:					web-allow-prod
Namespace:		default
Created on:		...
Labels: ...
...

Spec
  PodSelector: app=web # app=web 에 대해 적용되어 있다.
  Allowing ingress traffic:
  	To Port: 80/TCP # 80 포트에 적용되어 있음을 확인 가능하다.
  	From:
  		NamespaceSelector: purpose=production # namespaceSelector 에는 purpose=production 이 지정됐다.
  Not affecting egress traffic # egress 정책은 없다고 적혀있다.
  Policy Types: Ingress # Ingress 가 잘 적용되어있다.


```

<br/>



테스트

- purpose=production 에서는 app=web 으로 접근이 가능한지 확인
- purpose=development 에서는 app=web으로 접근이 불가능하도록 잘 적용되었는지 확인

<br/>



```bash
# 테스트를 위해 간단한 linux pod 를 testpod 라는 이름으로 띄운 후 그 안으로 접속해서 wget 또는 curl 요청을 app=web 으로 보내본다. linux pod 는 centos 이미지로 해도 되고 alpine 으로 해도 되는데, alpine 은 centos 에 비해 가볍기에 빠르게 설치되는 반면 가끔 wget 또는 curl 이 적용이 잘 안되는 경우가 있다. 따라서 가급적 시험장에서는 centos 를 선택하는 것이 좋은 선택이다. 

# -n dev 를 주어서 dev 네임스페이스를 지정서 testpod 라는 centos:7 이미지를 구동한 후 /bin/bash 로 접속
$ kubectl run testpod -it --rm --image=centos:7 -n dev -- /bin/bash

## 접속됨

# 위에서 확인했던 ip 주소 192.168.234.194 로 요청을 보내서 dev 네임스페이스에서 접속이 가능한지 테스트
$ curl 192.168.234.194 
연결 실패한다.

$ exit

# -n prod 를 주어서 prod 네임스페이스를 지정서 testpod 라는 centos:7 이미지를 구동한 후 /bin/bash 로 접속
$ kubectl run testpod -it --rm --image=centos:7 -n prod -- /bin/bash

# 위에서 확인했던 ip 주소 192.168.234.194 로 요청을 보내서 prod 네임스페이스에서 접속이 가능한지 테스트
$ curl 192.168.234.194 
연결 성공한다.
... 
<h1> Welcome to Nginx </h1>
...


# 테스트를 위해 생성한 webpod 삭제, 네임스페이스 dev, prod 삭제, NetoworkPolicy 도 삭제
$ kubectl delete pod webpod
$ kubectl delete namespace {dev,prod}
$ kubectl deleted networkpolicies web-allow-prod
...


```

<br/>



## 답

```bash
$ kubectl config use-context hk8s
$ kubectl run webpod --image=nginx --port=80 --labels=app=web 
$ kubectl get pods webpod -o wide


$ kubectl create namespace dev
$ kubectl create namespace prod 


$ kubectl label namespace prod purpose=production 
$ kubectl label namespace dev purpose=development 
$ kubectl get namespaces  --show-labels 


$ vi web-allow-prod.yaml
$ kubectl apply -f web-allow-prod.yaml

$ kubectl get networkpolicies.networking.k8s.io 
$ kubectl run test --namespace=dev --rm -it --image=alpine -- /bin/sh / 

# wget -qO- --timeout=2 http://web.default.svc.cluster.local 
$ kubectl run test --namespace=prod --rm -it --image=alpine -- /bin/sh / 

# wget -qO- --timeout=2 http://web.default.svc.cluster.local



$ kubectl delete networkpolicy web-allow-prod 
$ kubectl delete pod webpod 
$ kubectl delete namespace {prod,dev}
```

<br/>



# e.g. 2

> 작업 클러스터 : hk8s

default namespace 에 다음과 같은 pod 를 생성하세요.

- name : poc
- image : nginx
- port: 80
- label: app=poc

"partition=customera" 를 사용하는 namespace 에서만 poc 의 80 포트로 연결할 수 있도록 default namespace 에 'allow-web-from-customera' 라는 network policy 를 설정하세요. 보안정책상 다른 namespace의 접근은 제한합니다.<br/>



## 풀이

```bash
# hk8s 접속
$ kubectl config use-context hk8s

# nginx pod 구동, label 은 app=poc , port=80
$ kubectl run poc -it --image=nginx --label=app=poc --port=80

# 확인
$ kubectl get pod poc -o wide
... 		IP					...
...			192.168.75.105		...

# 네임스페이스들 조회 (label 이 partition 이 붙은)
# 이미 만들어져있다는 거 확인
$ kubectl get namespaces -L partition
NAME			...		PARTITION
customera				customera
customerb				customerb
...


# 북마크 NetowrkPolicy/ingress,egress 에 저장된 아래 도큐먼트 문서에서 기본 코드를 가져와서 작성한다.
# https://kubernetes.io/docs/concepts/services-networking/network-policies/#networkpolicy-resource

$ vi allow-web-from-customera.yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-web-from-customera #
  namespace: default
spec:
  podSelector:
    matchLabels:
      app: poc # 
  policyTypes:
  - Ingress
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          project: customera #
    ports:
    - protocol: TCP
      port: 80
  
#
$ kubectl apply -f allow-web-from-customera.yaml
networkpolicy.networking.k8s.io/allow-web-from-customera created


$ kubectl run test --image=centos:7 -it --rm -n customera -- /bin/bash
...


[user@console ~]# curl 192.168.75.105
... 

<h1>Welcome to nginx!</h1>

...


[user@console ~]# exit


```

<br/>



## 답

```bash
$ kubectl config use-context hk8s
$ kubectl get namespaces -L partition
$ kubectl run poc --image=nginx --port=80 --labels=app=poc 

$ kubectl get pod poc -o wide
$ vi allow-web-from-customera.yaml

$ kubectl apply -f allow-web-from-customera.yaml
$ kubectl run testpod -n customerb --image=centos:7 -it --rm -- /bin/bash 

/]# curl 192.168.75.100 --실패
/]# exit

$ kubectl run testpod -n customera --image=centos:7 -it --rm -- /bin/bash 

/]# curl 192.168.75.100
/]# exit
```

<br/>





