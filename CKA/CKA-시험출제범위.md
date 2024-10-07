# 시험 출제 범위

CKA 주관사인 cncf 공식 github 은 다음과 같다.

- https://github.com/cncf

여기서 [curriculum](https://github.com/cncf/curriculum) 이라는 리포지터리를 들어가보자.<br/>

<br/>



2024.10.07 현재 가장 최신 버전의 Curriculum 문서의 버전은 v1.31 이다.

- [CKA_Curriculum_v1.31.pdf](https://github.com/cncf/curriculum/blob/master/CKA_Curriculum_v1.31.pdf)

![](./img/cka-curriculum/1.png)

<br/>



Curriculum 에서 출제 비중을 정리해보면 다음과 같다. 사실은 캡처를 떴다가 pdf 의 내용을 복사해서 텍스트만 복사해왔다. 캡처는 그림만 멋있고 내용이 별로 없어서 한눈에 안들어와서다.<br/>

볼드체로 강조한 부분들이 중요하다는 것을 알 수 있다.<br/>

**25% - Cluster Architecture, Installation & Configuration**

- Manage role based access control (RBAC) 
- Use Kubeadm to install a basic cluster 
- Manage a highly-available Kubernetes cluster 
- Provision underlying infrastructure to deploy a Kubernetes cluster
- Perform a version upgrade on a Kubernetes cluster using Kubeadm
- Implement etcd backup and restore

15% - Workloads & Scheduling

- Understand deployments and how to perform rolling update and rollbacks
- Use ConfigMaps and Secrets to configure applications
- Know how to scale applications
- Understand the primitives used to create robust, self-healing, application deployments
- Understand how resource limits can affect Pod scheduling
- Awareness of manifest management and common templating tools

**20% - Services & Networking**

- Understand host networking configuration on the cluster nodes
- Understand connectivity between Pods
- Understand ClusterIP, NodePort, LoadBalancer service types and endpoints
- Know how to use Ingress controllers and Ingress resources
- Know how to configure and use CoreDNS
- Choose an appropriate container network interface plugin

10% - Storage

- Understand storage classes, persistent volumes
- Understand volume mode, access modes and reclaim policies for volumes
- Understand persistent volume claims primitive
- Know how to configure applications with persistent storage

**30% - Troubleshooting**

- Evaluate cluster and node logging
- Understand how to monitor applications
- Manage container stdout & stderr logs
- Troubleshoot application failure
- Troubleshoot cluster component failure
- Troubleshoot networking

