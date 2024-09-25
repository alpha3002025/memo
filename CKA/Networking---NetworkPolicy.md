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



참고)

- k8s 는 CNI로 플라넬을 사용한다.
- hk8s 는 CNI 로 칼리코를 사용한다.

NetworkPolicy 는 CNI 가 Network Policy 를 지원해야 사용가능하다. 플라넬은 NetworkPolicy 가 지원하지 않는다.

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
$ kubectl run test --namespace=dev --rm -it --image=alpine -- /bin/sh / # wget -qO- --timeout=2 http://web.default.svc.cluster.local $ kubectl run test --namespace=prod --rm -it --image=alpine -- /bin/sh / # wget -qO- --timeout=2 http://web.default.svc.cluster.local



$ kubectl delete networkpolicy web-allow-prod 
$ kubectl delete pod webpod 
$ kubectl delete namespace {prod,dev}
```

