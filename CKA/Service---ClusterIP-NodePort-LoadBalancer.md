# Service - ClusterIP,NodePort,LoadBalancer

Pod 의 단일 진입 지점은 LoadBalancer 인데 Service 는 LoadBalancer 를 지원해준다.<br/>

Service 에는 다음과 같은 타입들이 있다.

- ClusterIP (default) 
  - selector 의 label 이 같은 Pod를 그룹으로 묶어  단일 진입점(Virtual IP:LB) 생성
  - **Cluster 내부에서 사용하는 IP**
  - ClusterIP 로는 외부에서 특정 노드의 포트로 접근 불가 (노드 포트만 가능)
  - 외부에서 접속을 못하는 것으로 인해 불필요하다고 생각할수도 있겠지만, 그렇지 않다. pod 와 pod 간에 통신할 때 ClusterIP 가 유용하다.
  - Service type 을 생략하면 ClusterIP 타입이 default 로 설정된다.
  - ClusterIP 의 IP 주소는 10.96.0.0 대역, CIDR 은 12 의 범위에서 할당된다. 다시 한번 정리하지만 **Cluster 내부에서 사용하는 IP** 다. 

- NodePort
  - 포트를 기준으로 외부에서 접속할 수 있는 포인트를 제공하는 서비스
  - 이름에서 알 수 있듯이 **'노드 (Node)'** 의 특정 **'포트 (Port)'**를 Service 로 제공하는 것이다.
  - 개별 노드에 외부에서 접속할 수 있는 포트가 제공된다.
  - 포트의 범위는 30000 \~ 32767 이다.

- LoadBalancer
  - 클라우드에서 제공되는 리소스다.
  - Node Port 를 사용하더라도 앞단에 Load Balancer 를 두면 Load Balancer 를 통해서 여러 노드 중 하나를 선택하도록 로드밸런싱이 가능하다.
  - 클라우드 서비스에서는 LoadBalancer 를 자동으로 프로비저닝하는 기능을 지원하고 있다.





# 참고 : CNI (Container Network Interface)

CNI 는 ClusterIP 와는 다른 개념이다.<br/>

서로 다른 노드에 배포되어 있는 Pod 간의 네트워크 통신은 CNI (Container Network Interface)를 통해 가능해진다. CNI 를 지원하는 것들로는 칼리코, 플라넬 등이 있다.<br/>



# kubernetes 의 Service

Service 는 kubernetes API 중 일부이다. <br/>

![](./img/Service/3.png)

Service 는 etcd 에 저장된 노드들의 정보를 기반으로 통신을 하며 각 노드들에 위치한 kublet 에 iptables rule 작업을 요청한다.<br/>

각 노드에 설치된 kubelet 은 kube-proxy 에 iptables rule 작업을 요청한다.<br/>

<br/>



# virtual IP, Cluster IP

virtual IP 라고 하는 개념이 있는데 virtual IP는 여러 개의 파드 들을 하나의 ip 를 통해서 접근할 수 있도록 하는 개념이다. 쿠버네티스에서는 virtual IP 를 Cluster IP라고 부른다. **"클러스터에서 사용하는 IP"** 라는 의미로 기억하면 조금 더 직관적으로 이해가 된다.<br/>

![](./img/Service/1.png)

ClusterIP 의 IP 주소는 10.96.0.0 대역, CIDR 은 12 의 범위에서 할당된다. 다시 한번 정리하지만 **Cluster 내부에서 사용하는 IP** 다. <br/>

<br/>



# ClusterIP 와 LoadBalancer

![](./img/Service/4.png)

설명 추가 예정



# ClusterIP, etcd

쿠버네티스에 "ClusterIP 를 만들어서 원하는 특정 여러 파드 들을 하나의 IP로 관리해주세요" 라는 요청을 보내면, etcd 에는 Cluster IP 로 대표되는 virtual IP가 생성되며, virtualIP 를 통해서 접근할 수 있는 내부 ip 들이 etcd 에 저장되게 된다.

![](./img/Service/2.png)

**iptables Rule**<br/>

쿠버네티스 이론적으로는 서비스가 Cluster IP를 통해서 외부 요청들에 대해 필요한 내부 파드를 선택해서 연결시키는 것처럼 보인다. 엄청 자세하게 알고 싶은게 아니라면 딱 여기까지만 알면 된다. <br/>

그런데 실질적으로는 (내부적으로는) Cluster IP가 연결시켜주는 안 쪽의 동작에서는 실질적으로는 iptables rule 을 이용해서 Load Balancing 역할을 수행한다.<br/>



# e.g. 1) hello world

deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
spec:
  replica: 3
  selector:
    matchLabels:
      app: web-nginx # 'web-nginx' label 을 지정했다.
  template:
    metadata:
      name: nginx-pod
      labels: # label을 주목
        app: web-nginx # label 을 주목
    spec:
      containers:
      - name: nginx-container
        image: nginx:latest
```

<br/>



service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: web-svc
spec:
  clusterIP: 10.96.100.100 # clusterIP 를 지정했다.
  selector: # label 을 주목
    app: web-nginx # label 을 주목
  ports:
  - protocol: TCP
    port: 80
    targetPort: 80
```

<br/>



deployment 배포

```bash
$ kubectl apply -f deployment.yml
deployment.apps/web created

$ kubectl get pods | grep -i web-nginx
...
3개의 pod 가 기동된 것을 확인 가능

$ kubectl get pods -o wide | grep -i web-nginx
web-aaaaa1	... 	10.248.1.21		...
web-bbbbb1  ...   10.248.2.50		...
web-ccccc1  ...   10.248.1.20   ...
```

<br/>



service 배포

```bash
$ kubectl apply -f services.yml
service/web-svc created

# Cluster IP 가 생성되었는지 확인해보자.
$ kubectl get svc
NAME					TYPE				CLUSTER-IP				PORT(S)
kubernetes 		ClusterIP			10.96.0.1				443/TCP
web-svc				ClusterIP			10.96.100.100		80/TCP
```

<br/>



ClusterIP 가 정상동작하는지 확인

```bash
# 위에서 확인한 web-svc 라는 이름의 ClusterIP 의 CLUSTER-IP 인 10.96.100.100 을 복사해서 curl 로 접속해보자
$ curl 10.96.100.100
<!DOCTYPE html>
<html>
...
<h1> Welcome to nginx! </h1>
...

</html>
```

<br/>



위의 결과는 ClusterIP 인 10.96.100.100 으로 Request 를 보내면 내부에 배포된 Pod 3 개중 1개를 선택해서 요청이 전달되어 위와 같은 결과가 나타나는 것이다. 참고로 ClusterIP 는 Cluster 내에서만 사용 가능한 IP다.

<br/>



# ClusterIP

## 기출문제 1)

## 기출문제 2)



# NodePort

## NodePort 란?

## 기출문제 1)

## 기출문제 2)

