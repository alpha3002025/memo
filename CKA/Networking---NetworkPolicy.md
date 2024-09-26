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
  PodSelector: app=web
  Allowing ingress traffic:
  	To Port: 80/TCP
  	From:
  		NamespaceSelector: purpose=production
  Not affecting egress traffic
  Policy Types: Ingress


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

