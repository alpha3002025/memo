# 백준 medium

# 1. 11053 - 가장 긴 증가하는 부분 수열

## 문제 링크

- http://acmicpc.net/problem/11053



# 2. 1932 - 정수삼각형



# 3. 13549 - 숨바꼭질 3

## 문제 링크

- http://acmicpc.net/problem/13549



## 설명

bfs 다. PriorityQueue 를 사용할 때, 주의할 점은 pq.size() 로 호출하는 것이 아니라 pq.qsize() 로 호출해야 한다. 주의하자.

## 풀이

```python
from queue import PriorityQueue

n, k = map(int, input().split())

adj = [[] for _ in range(200001)]

for i in range(200001):
    if i > 0:
        adj[i].append((i-1, 1))
    if i < 200000:
        adj[i].append((i+1, 1))
    if 2 * i <= 200000:
        adj[i].append((2*i, 0))

dist = [1e9] * (200001)

pq = PriorityQueue()
pq.put((0, n))
dist[n] = 0


while pq.qsize() != 0:
    d, u = pq.get()

    if d != dist[u]:
        continue

    for v, cost in adj[u]:
        if dist[v] > dist[u] + cost:
            dist[v] = dist[u] + cost
            pq.put((dist[v], v))

print(dist[k])
```

<br/>



# 4. 8911 - 거북이

## 문제 링크

- http://acmicpc.net/problem/8911

<br/>

## 설명



## 풀이

```python
t = int(input())

directions = [(1,0), (0,1), (-1,0), (0,-1)]

for _ in range(t):
    move_history = input()

    did = 1
    x = 0
    y = 0

    minx = 0
    miny = 0
    maxx = 0
    maxy = 0
    for move in move_history:
        if move == 'L':
            did = (did + 1) % 4
        if move == 'R':
            did = (did + 3) % 4
        if move == 'F':
            x += directions[did][0]
            y += directions[did][1]
        if move == 'B':
            x -= directions[did][0]
            y -= directions[did][1]

        minx = min(minx, x)
        miny = min(miny, y)
        maxx = max(maxx, x)
        maxy = max(maxy, y)
    
    print((maxx - minx) * (maxy - miny))
```

<br/>



# 5. 1333 - 부재 중 전화

## 문제 링크

- http://acmicpc.net/problem/1333

<br/>



## 설명

- 노래의 곡 개수 N : 1 ≤ N ≤ 20
- 노래의 최대 길이 L : 1 ≤ L ≤ 180

<br/>

check 라고 불리는 체크 용도의 배열을 선언해서 사용한다.<br/>

최대 3600 까지가 최대 구간인데, 넉넉하게 잡아서 5000 정도를 최대치로 잡아서 배열이 0 \~ 4999 의 위치에 존재할 수 있도록 5000 크기의 배열을 하나 선언한다.<br/>

<br/>

![](./img/MEDIUM/1333-1.png)

<br/>



 ## 풀이

```python
n,l,d = map(int, input().split())

LIMIT = 5000
check = [False] * LIMIT
curr = 0

for each_song in range(n): # N 곡의 노래 
    for _ in range(l): # 노래 재생
        check[curr] = True
        curr += 1
        
    for _ in range(5): # 노래 쉬는 텀 5초
        curr += 1

for i in range(0, LIMIT, d):
    if not check[i]:
        print(i)
        break

```

<br/>



# 6. 1135 - 뉴스 전하기 (어렵다)

## 문제 링크

- http://acmicpc.net/problem/1135
- http://icpc.me/1135

## 설명

다이나믹 프로그래밍이다.<br/>

<br/>



트리 구성은 다음과 같이 한다.<br/>

![](./img/MEDIUM/1135-1.png)

<br/>

dfs 는 다음과 같이 순회한다.<br/>

제일 끝단의 원자적인, 기초적인 단위를 times\[i\] = 0 으로 해두고, i번째 요소를 순회시에 i+1 에 times\[i\] 를 구한값과 d\[i\] 와의 max비교로 구해낸다. i+1 을 하는 이유는 i 번째 index 를 더해가는게 아니라 사람을 몇번(i+1) 거쳐갔는지를 계산하기 위해서다.

![](./img/MEDIUM/1135-2.png)

<br/>



## 풀이

```python
n = int(input())

workers = list(map(int, input().split()))

child = [[] for _ in range(n)]

for i in range(1, n):
    child[workers[i]].append(i)

d = [0] * n

def dfs(u):
    # d[u] 값을 구하는 것이 목표

    times = []
    for v in child[u]:
        dfs(v)
        times.append(d[v])
    
    times.sort(reverse=True)

    # 번째 자녀가 n번째
    d[u] = 0
    for i in range(len(times)):
        d[u] = max(d[u], i + 1 + times[i])

dfs(0)
print(d[0])
```

<br/>



# 7. 2616 - 소형기관차 (어렵다)

다이나믹 프로그래밍



## 문제 링크

- http://acmicpc.net/problem/2616



## 설명

다이나믹 프로그래밍 문제다.

D(i)(j)

- 0 \~ i 번 객차를 소형기관차 j 개로 운반할 때 운반 가능한 최대 승객 인원
- D(n-1)(3)



i 번째 객차를 포함시키지 않고 j 개의 소형기관차를 구성할 때

- D(i-1)(j)

i 번째 객차를 포함시키고 j 개의 소형 기관차를 구성할 때 

- sum ((i-k+1, i) + 1) + D(i-k)(j-1)



설명이 좀 어렵다. 풀이를 보는 수 밖에 없다.



## 풀이

```python
n = int(input())
a = list(map(int, input().split()))
k = int(input())

psum = [0] * n
psum[0] = a[0]

for i in range(1, n):
    psum[i] = psum[i-1] + a[i]

d = [[0] * 4 for _ in range(n)] # n x 4
for i in range(1, 4):
    d[0][i] = a[0]

for i in range(1, n):
    for j in range(1, 4):
        d[i][j] = d[i-1][j]

        if i <= k-1:
            d[i][j] = max(d[i][j], psum[i])
        else:
            sum = psum[i] - psum[i-k]
            d[i][j] = max(d[i][j], sum + d[i-k][j-1])

print(d[n-1][3])
```

<br/>



# 8. 3273 - 두수의 합

## 문제 링크

- http://acmicpc.net/problem/3273

<br/>



## 설명

![](./img/MEDIUM/3273-1.png)

<br/>



## 풀이

```python
n = int(input())
a = list(map(int, input().split()))
x = int(input())

count = [0] * 1000001
for i in range(n):
    count[a[i]] += 1

answer = 0
for i in range((x+1) // 2):
    if x - i >= 1000000:
        continue
    answer += count[i] * count[x-i]

if x%2 == 0:
    answer += count[x//2] * (count[x//2] -1) // 2

print(answer)
```

<br/>



# 9. 2014 - 소수의 곱

## 문제링크

- http://acmicpc.net/problem/2014

## 설명

우선순위 큐를 활용하는 문제다.

- 주어진 k 개의 소수들을 우선순위 큐에 넣는다.

<br/>



그리고 다음의 2가지 연산들을 n 번 반복한다.

- 가장 작은 수 x 를 우선순위 큐에서 빼준다.
- x * P(k) 를 수행한 결과를 우선순위 큐에 넣어준다.
  - e.g. x * P(1), x * P(2), x * P(3), ...
  - 단, x*P(k) 가 이미 PriorityQueue 에 들어간적이 있다면 넣어주지 않는다. Dictionary 에 따로 출현했던 수 들을 기록해두고 딕셔너리에 이미 존재하는 수일 경우에는 skip 하는 식으로 진행한다.

<br/>

위의 두 연산을 반복한다.<br/>

<br/>



## 풀이

```python
import heapq

k, n = map(int, input().split())
numbers = list(map(int, input().split()))


heap = []
for num in numbers:
    heapq.heappush(heap, num)

v = 0
for _ in range(n):
    v = heapq.heappop(heap)

    for num in numbers:
        if v * num >= 2 ** 31:
            break
        
        heapq.heappush(heap, v * num)
        if v % num == 0:
            break

print(v)
```

<br/>



# 10. 13904 - 과제

두가지 풀이 방식 가능

- 우선순위 큐
- 정렬 (38번 참고)

<br/>



## 문제 링크

- https://www.acmicpc.net/problem/13904

## 설명

우선순위 큐 를 이용해 해결 가능<br/>

마감기한이 1000일이라고 한다. 이 경우 다음과 같은 연산으로 해결할 수 있다.

- (1) 1000일째에 할 과제 선택 : Priority Queue 에서 가장 w가 높은 과제를 꺼낸다 
- (2) 999일째에 할 과제 결정 : PriorityQueue 에서 가장 w가 높은 과제를 꺼낸다. ((1) 이후의 가장 큰 값을 뽑게된다.) 
- (3) 998일째에 할 과제 결정 : PriorityQueue 에서 가장 w가 높은 과제를 꺼낸다. ((2) 이후의 가장 큰 값을 뽑게 된다.)
- ...

 이와 같은 방식으로 구하는 것으로 해결 가능하다.<br/>

<br/>



PriorityQueue 에서 w 가 큰것부터 뽑으려면 다른 방법도 있겠지만, 조금 약간의 트릭을 쓴다면 -1 을 곱해서 PriorityQueue 에 w 를 대입하면, -w 가 대입되는데 이렇게 되면 w 가 큰 것이 가장 작은수가 되어 뽑힐 수 있다. 꺼내는 시점에는 다시 -1 을 곱해주어 원상태로 바꿔주면서 풀면 된다.<br/>

<br/>



## 풀이

```python
from queue import PriorityQueue

n = int(input())

due = [[] for _ in range(1001)]

for _ in range(n):
    d, w = map(int, input().split())
    due[d].append(w)

pq = PriorityQueue()

answer = 0

for i in range(1000, 0, -1):
    for w in due[i]:
        pq.put(-w)
    
    if pq.qsize() > 0:
        answer += -pq.get()

print(answer)
```

<br/>



# 11. 1967 - 트리의 지름

## 문제 링크

- http://acmicpc.net/problem/1967

<br/>

## 설명

다음과 같은 방식으로 푼다.

- 임의의 노드를 선택 후 그 노드를 기반으로 dfs 를 수행한다. 이때 가장 먼 노드 u 를 찾는다.
- 찾은 u 에서 dfs 를 수행한다. 이 때 u 에서 가장 먼 노드  v 를 찾는다.

<br/>



## 풀이

```python
import sys
sys.setrecursionlimit(10**9)

n = int(input())
adj = [[] for _ in range(n)]


for _ in range(n-1):
    u, v, w = map(int, input().split())
    u -= 1
    v -= 1

    adj[u].append((v,w))
    adj[v].append((u,w))


visit = [False] * n
dist = [0] * n

def dfs(u):
    visit[u] = True

    for v, w in adj[u]:
        if not visit[v]:
            dist[v] = dist[u] + w
            dfs(v)

dfs(0)

max_dist = 0
who = 0

for i in range(n):
    if max_dist < dist[i]:
        max_dist = dist[i]
        who = i

visit = [False] * n
dist = [0] * n
dfs(who)

max_dist = 0
for i in range(n):
    if max_dist < dist[i]:
        max_dist = dist[i]

print(max_dist)
```

<br/>



# 12. 5397 - 키로거

## 문제 링크

- http://acmicpc.net/problem/5397

<br/>

## 설명

스택 개념을 이용해 문제를 푼다.<br/>

스택 2개를 정의해서 사용한다.

![](./img/MEDIUM/5397-1.png)

<br/>



(1) 문자입력

- 스택1 에 문자를 추가

(2) 백스페이스

- 스택 1 의 가장 위에 있는 문자를 pop

(3) 커서를 왼쪽으로 이동

- 스택 1 에서 pop 후 스택 2에 push
- 이동으로 인해 커서의 오른쪽에 있게 되는 문자들을 push

(4) 커서를 오른쪽으로 이동

- 스택 2 에서 pop 후 스택 1 에 push
- 이동으로 인해 커서의 왼쪽에 있게 되는 문자들을 push

<br/>



## 풀이

```python
t = int(input())

for _ in range(t):
    user_input = input()

    stack1 = []
    stack2 = []

    for s in user_input:
        if s == '-':
            if len(stack1) > 0:
                stack1.pop(-1)
        
        elif s == '<':
            if len(stack1) > 0:
                stack2.append(stack1.pop(-1))
        
        elif s == '>':
            if len(stack2) > 0:
                stack1.append(stack2.pop(-1))

        else:
            stack1.append(s)
    
    print("".join(stack1) + "".join(stack2[::-1]))
```

<br/>



# 13. 3986 - 좋은 단어

## 문제 링크

- http://acmicpc.net/problem/3986

<br/>

## 설명

스택을 이용한 풀이다.

(1) len(stack) == 0 or stack 의 top != i번째 문자

- stack 에 i 번째 문자를 추가한다

(2) stack[-1] == i번째 문자

- stack 에서 문자 하나를 pop 한다.

(1) 과 (2) 를 주어진 문자에 대해 반복했을 때 스택이 비어있지 않다면 좋은 문자이다. 따라서 count + 1 을 해준다.



## 풀이

```python
n = int(input())

count = 0
for _ in range(n):
    user_input = input()

    stack = []
    for s in user_input:
        if len(stack) == 0 or stack[-1] != s:
            stack.append(s)
        else:
            stack.pop(-1)
    
    if len(stack) == 0:
        count += 1

print(count)
```

<br/>



# 14. 1253 - 좋다

## 문제 링크

- http://acmicpc.net/problem/1253

<br/>



## 설명

![](./img/MEDIUM/1253-1.png)

<br/>



## 풀이

```python
n = int(input())
a = list(map(int, input().split()))

a.sort()
count = 0

for i in range(n):
    k = n-1
    if k == i:
        k -= 1
    
    found = False
    for j in range(n):
        if j == i:
            continue

        target = a[i] - a[j]

        while j < k and a[k] > target:
            k -= 1
            if k == i:
                k -= 1
        
        if j != k and a[k] == target:
            found = True
            break

    if found:
        count += 1

print(count)
```

<br/>



# 15. 16562 - 친구비

## 문제 링크

- https://www.acmicpc.net/problem/16562



## 설명

bfs 문제다.

![](./img/MEDIUM/16562-1.png)

<br/>



## 풀이

```python
from collections import deque

n,m,k = map(int, input().split())
a = list(map(int, input().split()))
adj = [[] for _ in range(n)]

for _ in range(m):
    u, v = map(int, input().split())
    u -= 1
    v -= 1
    adj[u].append(v)
    adj[v].append(u)

visit = [False] * n
queue = deque()

total = 0
for i in range(n):
    if visit[i]:
        continue

    queue.append(i)
    visit[i] = True
    min_cost = a[i]

    while len(queue) != 0:
        u = queue.popleft()

        for v in adj[u]:
            if not visit[v]:
                queue.append(v)
                visit[v] = True
                min_cost = min(min_cost, a[v])
    
    total += min_cost

if total <= k:
    print(total)
else:
    print("Oh no")

```

<br/>



# 16. 2914 - 저작권

## 문제링크

- http://acmicpc.net/problem/2914

<br/>

## 설명

- (x / A) 의 올림 값이 I 가 되도록 하는 x 의 최소값을 구하는 문제

이때 I 가 (I-1) + 0.5xxxxx 값이면 올림 처리가 되어 I 가 된다.<br/>

따라서 아래 두 경우를 생각해볼 수 있다.

- (1) : x/A = I  (x를 A 로 나눈 값이 정확하게 I 인 경우)
- (2) : x/A = (I-1) + 0.5xxxxxxx 이상인 경우 



식으로 표현하면 다음과 같이 표현된다.

- (I-1) \< x/A \<= I

<br/>



## 풀이

```python
a,i = map(int, input().split())

print((i-1) * a + 1)
```

<br/>



# 17. 2629 - 양팔저울

## 문제 링크

- http://acmicpc.net/problem/2629

<br/>

## 설명

다이나믹 프로그래밍 문제다

- D(i)(j) = 0 \~ i 번의 추를 사용해서 j 그램을 만들수 있는지?
  - 만들 수 있다면 True
  - 만들 수 없다면 False

<br/>

D(i)(j)의 점화식

- i번 추를 사용하지 않는 경우 : D(i-1)(j)
- i번 추를 사용하는 경우
  - 물건을 같이 올리는 경우 : D(i-1)(j + a\[i\])
    - i-1 번째까지 추를 사용해서 j + a\[i\] 를 만들 수 있는지를 체크
  - 물건을 반대편에 올리는 경우 : D(i-1)(j - a\[i\])
    - i-1 번째까지 추를 사용해서 j - a\[i\] 를 만들 수 있는지를 체크

<br/>



## 풀이

```python
n = int(input())
a = list(map(int, input().split()))
m = int(input())
b = list(map(int, input().split()))

d = [[False] * 80002 for _ in range(n)]

# d[i][j] 0~i -> j
# j < 0, 0 -> 40001

zero = 40001

d[0][zero] = True
d[0][zero + a[0]] = True
d[0][zero - a[0]] = True

for i in range(1, n):
    for j in range(0, 80002):
        d[i][j] = d[i-1][j]

        if j-a[i] >= 0:
            d[i][j] |= d[i-1][j-a[i]]
        if j+a[i] < 80002:
            d[i][j] |= d[i-1][j+a[i]]

for i in range(m):
    if d[n-1][zero + b[i]]:
        print("Y", end=" ")
    else:
        print("N", end=" ")
```

<br/>



# 18. 15989 - 1,2,3 더하기 4

## 문제 링크

- https://www.acmicpc.net/problem/15989

<br/>



## 설명

D(i)(j)

- 1 \~ i 까지의 수를 사용해서 j 를 만드는 경우의 수
- 구하고자 하는 값 : D(3)(n)

점화식

- i 라는 수를 사용할 경우 : D(i-1)(j)
- i 라는 수를 사용하지 않을 경우 : D(i)(j-1)

<br/>

결론

- D(i)(j) = D(i-1)(j) + D(i)(j)

<br/>



## 풀이

```python
t = int(input())

d = [[0] * 10001 for _ in range(4)]
d[0][0] = 1

for i in range(1, 4):
    for j in range(10001):
        d[i][j] = d[i-1][j]

        if j >= i:
            d[i][j] += d[i][j-i]

for _ in range(t):
    n = int(input())
    print(d[3][n])
```

<br/>



# 19. 11508 - 2+1 세일

## 문제 링크

- http://acmicpc.net/problem/11508

<br/>



## 설명

![](./img/MEDIUM/11508-1.png)

<br/>



## 풀이

```python
n = int(input())
a = [int(input()) for _ in range(n)]

a.sort(reverse=True)
discount = 0

for i in range(0, n, 3):
    if i+2 < n:
        discount += a[i + 2]

print(sum(a) - discount)
```

<br/>



# 20. 13334 - 철로

## 문제 링크

- http://acmicpc.net/problem/13334

<br/>



## 설명

- 구간의 길이 \> D 인 경우 : 문제를 잘 읽어보면 이 경우는 없다는 것을 알 수 있다.
- 전체 - 구간 밖에 걸쳐 있는 철로를 계산해야 할 경우
  - 구간 시작점 \< 철로 시작점
  - 구간 끝 점 \> 철로 끝 점

<br/>



풀이 절차 

- (1) : 우선순위 큐에 모든 구간의 끝 점을 넣어준다.
- (2) : 모든 구간을 시작점을 기준으로 정렬해준다.
- (3) : 철로의 시작점보다 앞에 있는 것과 끝점보다 뒤에 있는 것들을 구해준다.

구간의 끝 점이 철로 안쪽에 있는 경우는 모두 우선순위 큐에서 빼주면 된다.<br/>

이렇게 하면 구간의 끝 점이 철로의 끝 지점을 넘어서는 경우들만 남아있게 된다.<br/>

여기부터는 철로를 한번씩 남은 구간  k 를 하나씩 돌면서 각 구간의 시작점에 둔 후 차례대로 각 구간에 대해 우선순위 큐를 비워나간다.

<br/>

## 풀이

```python
from queue import PriorityQueue
n = int(input())
r = [list(map(int, input().split())) for _ in range(n)]
d = int(input())

x = []
for i in range(n):
    if r[i][0] > r[i][1]:
        r[i][0], r[i][1] = r[i][1], r[i][0]
    if r[i][1] - r[i][0] > d:
        continue
    x.append(r[i])

n = len(x)
x.sort()
pq = PriorityQueue()
for i in range(n):
    pq.put(x[i][1])

answer = 0
for i in range(n):
    while pq.qsize() != 0:
        curr = pq.get()
        if curr > x[i][0] + d:
            pq.put(curr)
            break
    
    answer = max(answer, n - i - pq.qsize())

print(answer)
```

<br/>



# 21. 2213 - 트리의 독립집합

## 문제 링크

- http://acmicpc.net/problem/2213

<br/>



## 설명

다이나믹 프로그래밍 문제다.<br/>



## 풀이

```python
n = int(input())
a = list(map(int, input().split()))
adj = [[] for _ in range(n)]

for _ in range(n-1):
    u, v = map(int, input().split())
    u -= 1
    v -= 1

    adj[u].append(v)
    adj[v].append(u)

d = [0] * n
e = [0] * n

d_sol = [[] for _ in range(n)]
e_sol = [[] for _ in range(n)]

visit = [False] * n

def dfs(u):
    visit[u] = True

    d[u] = a[u]
    d_sol[u].append(u)
    e[u] = 0

    for v in adj[u]:
        if not visit[v]:
            dfs(v)
            d[u] += e[v]
            d_sol[u].extend(e_sol[v])

            if d[v] < e[v]:
                e[u] += e[v]
                e_sol[u].extend(e_sol[v])
            else:
                e[u] += d[v]
                e_sol[u].extend(d_sol[v])

dfs(0)

if d[0] < e[0]:
    print(e[0])
    e_sol[0].sort()
    print(*list(map(lambda x: x+1, e_sol[0])))
else:
    print(d[0])
    d_sol[0].sort()
    print(*list(map(lambda x: x+1, d_sol[0])))
```

<br/>



# 22. 16437 - 양 구출 작전

## 문제 링크

- https://www.acmicpc.net/problem/16437

<br/>



## 설명

<br/>



## 풀이

```python
import sys
sys.setrecursionlimit(123458)

n = int(input())
arr = [0] * n
child = [[] for _ in range(n)]


for i in range(1, n):
    t, a, p = input().split()
    a = int(a)
    p = int(p) - 1

    if t == 'S':
        arr[i] = a
    else:
        arr[i] = -a

    child[p].append(i)

d = [0] * n

def dfs(u):
    d[u] = arr[u]
    for v in child[u]:
        dfs(v)
        d[u] += d[v]
    
    if d[u] < 0:
        d[u] = 0

dfs(0)
print(d[0])
```

<br/>



# 23. 2980 - 도로와 신호등

## 문제 링크

- http://acmicpc.net/problem/2980

<br/>



## 설명

<br/>



## 풀이

```python
n, l = map(int, input().split())
info = [list(map(int, input().split())) for _ in range(n)]

state = ['R'] * n
remain = [info[i][1] for i in range(n)]

pos = 0

for i in range(10000):
    if pos == l:
        print(i)
        break

    stop = False
    for j in range(n):
        if info[j][0] == pos:
            if state[j] == 'R':
                stop = True
                break
    
    if not stop:
        pos += 1
    
    for j in range(n):
        remain[j] -= 1

        if remain[j] == 0:
            remain[j] = info[j][1] if state[j] == 'G' else info[j][2]
            state[j] = 'R' if state[j] == 'G' else 'G'
```

<br/>



# 24. 2381 - 최대 거리

## 문제 링크

- http://acmicpc.net/problem/2381

<br/>



## 설명

<br/>



## 풀이

```python
n = int(input())
p = [list(map(int, input().split())) for _ in range(n)]

max_plus = -1e9
min_plus = 1e9

for x,y in p:
    max_plus = max(max_plus, y + x)
    min_plus = min(min_plus, y + x)

max_minus = -1e9
min_minus = 1e9
for x,y in p:
    max_minus = max(max_minus, y-x)
    min_minus = min(min_minus, y-x)

print(max(max_plus - min_plus, max_minus - min_minus))
```

<br/>



# 25. 12919 - A와 B 2

## 문제 링크

- http://acmicpc.net/problem/12919

<br/>



## 설명

<br/>



## 풀이

```python
s = input()
t = input()

def dfs(input):
    if len(input) == len(s):
        return input == s
    
    if input[-1] == 'A':
        if dfs(input[:-1]):
            return True
    
    if input[0] == 'B':
        if dfs(input[1:][::-1]):
            return True
        
    return False

if(dfs(t)):
    print(1)
else:
    print(0)
```

<br/>



# 26. 9657 - 돌 게임 3

## 문제 링크

- http://acmicpc.net/problem/9657



## 설명



## 풀이

```python
n = int(input())
d = [False] * 1001

d[1] = True
d[2] = False
d[3] = True
d[4] = True

for i in range(5, 1001):
  d[i] = (not d[i-1]) | (not d[i-3]) | (not d[i-4])

if d[n]:
  print("SK")
else:
  print("CY")
```





# 27. 11060 - 점프 점프

## 문제 링크

- http://acmicpc.net/problem/11060



## 설명

## 풀이

```python
n = int(input())
a = list(map(int, input().split()))

d = [1e9] * n
d[n-1] = 0

for i in range(n-2, -1, -1):
    d[i] = 1e9
    
    for j in range(1, a[i] + 1):
        if i + j >= n:
            break
            
        d[i] = min(d[i], 1 + d[i+j])

if d[0] == 1e9:
    print(-1)
else:
    print(d[0])
```





# 28. 15681 - 트리와 쿼리

## 문제 링크

- http://acmicpc.net/problem/15681

<br/>

## 설명

## 풀이

```python
import sys
sys.setrecursionlimit(100000000)
input = sys.stdin.readline

n,r,q = map(int, input().split())
r -= 1
adj = [[] for _ in range(n)]

for _ in range(n-1):
    u,v = map(int, input().split())
    u -= 1
    v -= 1

    adj[u].append(v)
    adj[v].append(u)


visit = [False] * n
d = [0] * n

def dfs(u):
    visit[u] = True

    d[u] = 1
    for v in adj[u]:
        if not visit[v]:
            dfs(v)
            d[u] += d[v]

dfs(r)

for _ in range(q):
    u = int(input())
    u -= 1

    print(d[u])
```

<br/>



# 29. 2367 - 장난감 조립

## 문제 링크

- http://acmicpc.net/problem/2367



## 설명

## 풀이

````python
from collections import deque

n = int(input())
m = int(input())
adj = [[] for _ in range(n)]
count = [0] * n

for _ in range(m):
    x,y,k = map(int, input().split())
    x -= 1
    y -= 1

    adj[x].append((y, k))
    count[y] += 1


answer = [0] * n 
answer[n-1] = 1

queue = deque()
for i in range(n):
    if count[i] == 0:
        queue.append(i)

while len(queue) != 0:
    u = queue.popleft()

    for v,w in adj[u]:
        answer[v] += w*answer[u]
        count[v] -= 1
        if count[v] == 0:
            queue.append(v)

for i in range(n):
    if len(adj[i]) == 0:
        print(i+1, answer[i])
````

<br/>



# 30. 12919 - A와  B 2 (중복)

## 문제 링크

## 설명

## 풀이





# 31. 28353 - 고양이 카페

## 문제 링크

- https://www.acmicpc.net/problem/28353

<br/>

## 설명

## 풀이

```python
n, k = map(int, input().split())
arr = list(map(int, input().split()))

arr.sort()
start = 0
end = n - 1

count = 0
while start < end:
    if arr[start] + arr[end] <= k:
        count += 1
        start += 1
        end -= 1
    else:
        end -= 1

print(count)
```

<br/>



# 32. 3673 - 나눌 수 있는 부분 수열

## 문제 링크

- https://www.acmicpc.net/problem/3673

<br/>

## 설명

## 풀이

```python
t = int(input())

for _ in range(t):
    d,n = map(int, input().split())
    arr = list(map(int, input().split()))

    psum = [0] * n
    psum[0] = arr[0]

    for i in range(1, n):
        psum[i] = psum[i-1] + arr[i]
    
    answer = 0
    count = {}
    count[0] = 1

    for i in range(n):
        if psum[i] % d not in count:
            count[psum[i] % d] = 0
        answer += count[psum[i] % d]
        count[psum[i] % d] += 1
    
    print(answer)
```

<br/>



# 33. 3184 - 양 (안풀림)

## 문제 링크

- https://www.acmicpc.net/problem/3184

<br/>

## 설명

## 풀이

```python
from collections import deque

r,c = map(int, input().split())
b = [input() for _ in range(r)]

visit = [[False] * c for _ in range(r)]
queue = deque()


dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]

final_o = 0
final_v = 0

for i in range(r):
    for j in range(c):
        if b[i][j] != '#' and not visit[i][j]:
            queue.append((i,j))
            visit[i][j] = True

            o_count = 1 if b[i][j] == 'o' else 0
            v_count = 1 if b[i][j] == 'v' else 0

            while len(queue) != 0:
                r,c = queue.popleft()

                for k in range(4):
                    nr, nc = r + dr[k], c + dc[k]

                    if nr < 0 or r <= nr or nc < 0 or c <= nc:
                        continue

                    if b[nr][nc] == '#':
                        continue

                    if not visit[nr][nc]:
                        queue.append((nr, nc))
                        visit[nr][nc] = True

                        if b[nr][nc] == 'o':
                            o_count += 1
                        if b[nr][nc] == 'v':
                            v_count += 1

            if o_count > v_count:
                final_o += o_count
            else:
                final_v += v_count

print(final_o, final_v)
```

<br/>



# 34. 12886 - 돌 그룹

## 문제 링크

- https://www.acmicpc.net/problem/12886

## 설명

## 풀이

```python
from collections import deque

a,b,c = map(int, input().split())
s = a + b + c

if s % 3 != 0:
    print(0)
else:
    visit = [[False] * s for _ in range(s)]
    queue = deque()
    queue.append((a,b))
    visit[a][b] = True

    while len(queue) != 0:
        a,b = queue.popleft()
        c = s - a - b

        d = [a, b, c]
        for i in range(3):
            x,y = d[i], d[(i+1)%3]

            if x == y:
                continue
            if x > y:
                x,y = y,x
            
            if not visit[x + x][y - x]:
                queue.append((x+x, y-x))
                visit[x+x][y-x] = True
    
    if visit[s//3][s//3]:
        print(1)
    else:
        print(0)
```





# 35. 16198 - 에너지 모으기

## 문제 링크

- https://www.acmicpc.net/problem/16198

<br/>



## 설명

## 풀이

```python
n = int(input())
arr = list(map(int, input().split()))

def dfs(a, w):
    if len(a) == 2:
        return w
    
    ret = 0
    for i in range(1, len(a) - 1):
        na = a[:i] + a[i+1:]
        tmp = dfs(na, w + a[i-1] * a[i+1])
        if ret < tmp:
            ret = tmp
    
    return ret

print(dfs(arr, 0))
```

<br/>



# 36. 10211 - Maximum Subarray

## 문제 링크

- https://www.acmicpc.net/problem/10211

<br/>

## 설명

## 풀이

```python
t = int(input())

for _ in range(t):
    n = int(input())
    arr = list(map(int, input().split()))

    psum = [0] * n
    psum[0] = arr[0]

    for i in range(1,n):
        psum[i] = psum[i-1] + arr[i]
    
    max_sum = -1e9
    for i in range(n):
        for j in range(i, n):
            range_sum = psum[j]
            if i > 0:
                range_sum -= psum[i-1]
            if max_sum < range_sum:
                max_sum = range_sum
    
    print(max_sum)
```

<br/>



# 37. (중복) 10211 - Maximum Subarray

## 문제 링크

- https://www.acmicpc.net/problem/10211

<br/>

## 설명

## 풀이

```python
t = int(input())

for _ in range(t):
    n = int(input())
    arr = list(map(int, input().split()))

    psum = [0] * n
    psum[0] = arr[0]

    for i in range(1,n):
        psum[i] = psum[i-1] + arr[i]
    
    max_sum = -1e9
    for i in range(n):
        for j in range(i, n):
            range_sum = psum[j]
            if i > 0:
                range_sum -= psum[i-1]
            if max_sum < range_sum:
                max_sum = range_sum
    
    print(max_sum)
```

<br/>



# 38. 13904 - 과제

두가지 풀이 방식 가능

- 우선순위 큐 (10번 참고)
- 정렬

<br/>



## 문제 링크

- https://www.acmicpc.net/problem/13904

<br/>

## 설명

이번에는 정렬로 풀어본다.

## 풀이

```python
n = int(input())
arr = [list(map(int, input().split())) for _ in range(n)]

arr.sort(key=lambda x: x[1], reverse=True)

s = [False] * 1001
answer = 0

for i in range(n):
    for j in range(arr[i][0], 0, -1):
        if not s[j]:
            s[j] = True
            answer += arr[i][1]
            break

print(answer)
```

<br/>



# 39. 13398 - 연속합 2

## 문제 링크

- https://www.acmicpc.net/problem/13398

<br/>



## 설명

## 풀이

```python
n = int(input())
arr = list(map(int, input().split()))

d = [-1e9] * n
e = [-1e9] * n

d[0] = arr[0]
for i in range(1, n):
    d[i] = max(arr[i], arr[i] + d[i-1])

e[n-1] = arr[n-1]
for i in range(n-2, -1, -1):
    e[i] = max(arr[i], arr[i] + e[i+1])

answer = max(d)
for i in range(1, n-1):
    answer = max(answer, d[i-1] + e[i+1])

print(answer)
```

<br/>



# 40. 13398 - 연속합 2

## 문제 링크

- https://www.acmicpc.net/problem/13398

## 설명

## 풀이

```python
n = int(input())
arr = list(map(int, input().split()))

d = [-1e9] * n
e = [-1e9] * n

d[0] = arr[0]

for i in range(1, n):
    d[i] = max(arr[i], arr[i] + d[i-1])
    if i >= 2:
        e[i] = max(arr[i] + e[i-1], arr[i] + d[i-2])

print(max(max(d), max(e)))
```



<br/>



# 41. 1173 - 운동

## 문제 링크

- https://www.acmicpc.net/problem/1173

## 설명

## 풀이

```python
N, m, M, T, R = map(int, input().split())

if m + T > M:
    print(-1)
else:
    timer = 0
    X = m
    while N > 0:
        timer += 1

        if X + T <= M:
            X += T
            N -= 1
        else:
            X -= R
            X = max(X, m)
    
    print(timer)
```





<br/>



# 42. 1713 - 후보 추천하기

## 문제 링크

- https://www.acmicpc.net/problem/1713



## 설명

## 풀이

```python
n = int(input())
m = int(input())
c = list(map(int, input().split()))

count = [0] * 100
last = [-1] * 100

for i in range(m):
    c[i] -= 1

    count[c[i]] += 1

    if last[c[i]] != -1:
        continue

    picture = 0
    for j in range(100):
        if last[j] != -1:
            picture += 1
    
    if picture == n:
        # 탈락
        min_count = 1e9
        min_last = 1e9
        who = -1
        for j in range(100):
            if last[j] != -1:
                if min_count > count[j]:
                    min_count = count[j]
                    min_last = last[j]
                    who = j
                elif min_count == count[j] and min_last > last[j]:
                    min_last = last[j]
                    who = j
        
        count[who] = 0
        last[who] = -1
    
    # 추가
    last[c[i]] = i

for i in range(100):
    if last[i] != -1:
        print(i + 1, end=" ")
```

<br/>



# 43. 14470 - 전자레인지

## 문제 링크

- https://www.acmicpc.net/problem/14470

<br/>



## 설명

## 풀이

```python
A = int(input())
B = int(input())
C = int(input())
D = int(input())
E = int(input())

if A < 0 and B < 0:
    print((B-A)*C)
elif A < 0 and B > 0:
    print(-A * C + D + B * E)
else:
    print((B-A)*E)
```

<br/>



# 44. 14471 - 포인트 카드

## 문제 링크

- https://www.acmicpc.net/problem/14471

## 설명

## 풀이

```python
n, m = map(int, input().split())
c = [list(map(int, input().split())) for _ in range(m)]

c.sort(reverse=True)
cost = 0

for i in range(m-1):
    if c[i][0] < n:
        cost += n - c[i][0]

print(cost)
```

<br/>



# 45. 10040 - 투표

## 문제 링크

- https://www.acmicpc.net/problem/10040

## 설명

## 풀이

```python
n,m = map(int, input().split())
a = [int(input()) for _ in range(n)]
b = [int(input()) for _ in range(m)]

count = [0] * n

for i in range(m):
    for j in range(n):
        if a[j] <= b[i]:
            count[j] += 1
            break

max_vote = 0
who = -1
for i in range(n):
    if max_vote < count[i]:
        max_vote = count[i]
        who = i

print(who + 1)
```

<br/>



# 46. 10041 - 관광

## 문제 링크

- https://www.acmicpc.net/problem/10041



## 설명

## 풀이

```python
w,h,n = map(int, input().split())
P = [list(map(int, input().split())) for _ in range(n)]

answer = 0
for i in range(n-1):
    x1, y1 = P[i]
    x2, y2 = P[i+1]

    if x1 > x2:
        x1, x2 = x2, x1
        y1, y2 = y2, y1

    if y1 > y2:
        answer += abs(x1 - x2) + abs(y1 - y2)
    else:
        answer += max(abs(x1 - x2), abs(y1 - y2))

print(answer)
```

<br/>



# 47. 10710 - 실크로드

## 문제 링크

- https://www.acmicpc.net/problem/10710

<br/>

## 설명

## 풀이

```python
n,m = map(int, input().split())
d = [int(input()) for _ in range(n)]
c = [int(input()) for _ in range(m)]

dp = [[1e9] * (n+1) for _ in range(m+1)]
dp[0][0] = 0

for i in range(1, m+1):
    for j in range(0, n+1):
        dp[i][j] = dp[i-1][j]
        if j>0:
            dp[i][j] = min(
                dp[i][j],
                dp[i-1][j-1] + c[i-1] * d[j-1],
            )

print(dp[m][n])
```

<br/>



# 48. 18242 - 네모네모 시력검사

## 문제 링크

- https://www.acmicpc.net/problem/18242

## 설명

## 풀이

```python
n,m = map(int, input().split())
b = [input() for _ in range(n)]

min_y, max_y, min_x, max_x = 1e9, 0, 1e9, 0

for i in range(n):
    for j in range(m):
        if b[i][j] == '#':
            min_y = min(min_y, i)
            max_y = max(max_y, i)

            min_x = min(min_x, j)
            max_x = max(max_x, j)

center_y, center_x = (min_y + max_y) // 2, (min_x + max_x) // 2

if b[min_y][center_x] == '.':
    print("UP")
elif b[max_y][center_x] == '.':
    print("DOWN")
elif b[center_y][min_x] == '.':
    print("LEFT")
else:
    print("RIGHT")
```

<br/>



# 49. 31738 - 매우 어려운 문제

## 문제 링크

- https://www.acmicpc.net/problem/31738

## 설명

## 풀이

```python
n, m = map(int, input().split())

if n >= m:
    print(0)
else:
    d = 1
    for i in range(1, n+1):
        d *= i
        d %= m
    
    print(d)
```

<br/>





# 50. 25418 - 정수  a 를 k 로 만들기

## 문제 링크

- https://www.acmicpc.net/problem/25418

## 설명

## 풀이

```python
from collections import deque

a,k = map(int, input().split())
adj = [[] for _ in range(k+1)]

for i in range(1, k+1):
    if i+1 <= k:
        adj[i].append(i+1)
    if 2*i <= k:
        adj[i].append(2*i)

visit = [False] * (k+1)
dist = [-1] * (k+1)
queue = deque()

queue.append(a)
visit[a] = True
dist[a] = 0

while len(queue) != 0:
    u = queue.popleft()

    for v in adj[u]:
        if not visit[v]:
            queue.append(v)
            visit[v] = True
            dist[v] = dist[u] + 1

print(dist[k])
```

<br/>



# 51. 25418 - 정수  a 를 k 로 만들기

## 문제 링크

- https://www.acmicpc.net/problem/25418

## 설명

## 풀이

```python
a, k = map(int, input().split())

count = 0
while k > a:
    if k % 2 == 0 and k / 2 >= a:
        k //= 2
    else:
        k -= 1
    count += 1

print(count)
```

<br/>



# 52. 1419 - 등차수열의 합

## 문제 링크

- https://www.acmicpc.net/problem/1419

## 설명

## 풀이

```python
l = int(input())
r = int(input())
k = int(input())


if k == 2:
    upper = max(0, r-2)
    lower = max(0, l-3)
    print(upper - lower)
elif k == 3:
    upper = max(0, r//3 -1)
    lower = max(0, (l-1)//3 -1)
    print(upper - lower)
elif k == 4:
    upper = max(0, r//2 -4)
    lower = max(0, (l-1)//2 -4)
    if r >= 12:
        upper -= 1
    if l-1 >= 12:
        lower -= 1
    
    print(upper - lower)
else:
    upper = max(0, r//5 -2)
    lower = max(0, (l-1)//5 - 2)
    print(upper - lower)
```

<br/>



# 53. 27970 - OX

## 문제 링크

- https://www.acmicpc.net/problem/27970

## 설명

## 풀이

```python
s = input()
mod = int(1e9 + 7)

answer = 0
power = 1
for i in range(len(s)):
    if s[i] == 'O':
        answer += power
        answer %= mod
    power *= 2
    power %= mod

print(answer)
```

<br/>



# 54. 25186 - INFP 두람

## 문제 링크

- https://www.acmicpc.net/problem/25186

## 설명

## 풀이

```python
n = int(input())
a = list(map(int, input().split()))

total = sum(a)
if total != 1 and max(a) > total / 2:
    print("Unhappy")
else:
    print("Happy")
```

<br/>









