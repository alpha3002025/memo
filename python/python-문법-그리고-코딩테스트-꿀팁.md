

## 코딩테스트로 파이썬 언어 사용시 꿀팁

### Java 등 다른 언어의 스타일로 파이썬 코드를 작성하지 말자

Java 등에 존재하는 getter/setter 로 값을 받아오고 접근하는 기능을 python 에서도 똑같이 작성할 경우 개발 효율이 떨어집니다. 기본적으로 python 은 공개 속성이기 때문에 그럴 필요가 없고 이렇게 할 경우 성능적인 문제를 가지게 됩니다. 만약 getter/setter가 필요하면 `@property` 데코레이터를 사용하시는 것을 권장합니다.<br/>

<br/>



# 시간복잡도 줄이기

## readline() : 입력 속도를 빠르게 

데이터를 입력받아야 하는 경우 가급적 sys 모듈의 readline() 이 효율적입니다.<br/>

input() 이라는 함수를 사용할수도 있겠지만, input() 보다는 readline() 이 효율적입니다.

```python
import sys
data = sys.stdin.readline()
```

<br/>



## 리스트 초기화 : 리스트의 곱셈

리스트 초기화 시 미리 할당해놓은 고정 배열 처럼 선언하는 것이 시간복잡도 측면에서는 더 효율적입니다<br/>

이 경우 리스트에 곱셈 연산을 해서 초기화와 할당을 동시에 하도록 만들수 있습니다.

```python
data1 = [0 for _ in range(1000)] # 동적으로 배열에 데이터를 계속해서 추가
data2 = [0] * 1000 # 초기화와 함께 할당을 수행
```

- data1 : 리스트에 append() 하는 것을 반복
- data2 : [0] 을 반복 하는 연산

<br/>



## 간단한 리스트는 컴프리헨션, 사이즈가 크면 제너레이터를 사용

리스트에 1\~ 10 까지의 데이터를 넣을때 range() 함수를 사용하면 아래와 같은 코드로 데이터를 넣을 수 있습니다.

```python
list = []

for i in range(1, 11):
    list.append(i)

print(f"list = {list}")
```

<br/>



### 컴프리헨션

위의 코드는 컴프리헨션으로 표현하면 아래와 같이 짧게 표현됩니다.

```python
list = [i for i in range(1, 11)]

print(f"list = {list}")
```

<br/>

위의 코드에서는 리스트(list) 자료형에 대한 컴프리헨션이었습니다.

컴프리헨션은 아래와 같은 여러가지 자료형에 대하 모드 사용 가능합니다.

- list
- tuple
- set
- dict

<br/>



리스트 컴프리헨션의 문법은 아래와 같습니다.

- `[(변수표현식) for (사용할 변수) in (순회가능한 연속적인 데이터)]`

<br/>



컴프리헨션에 조건문을 사용하려면 맨 뒤에 사용하면되는데 예제는 다음과 같습니다.<br/>

조건문을 통해 짝수와 5의 배수를 동시에 만족하는 1 \~ 10 사이의 숫자의 리스트를 얻어내는 예제입니다.

```python
list = [i for i in range(1,11) if i%2 == 0 and i%5 == 0]

print(f"list = {list}")
```

<br/>



컴프리헨션은 2차원 배열을 1차원 배열로 압축(flatten)하거나 데이터의 구조를 직접적으로 변형하지 않고도 원하는 연산을 수행할 수 있도록 해줍니다. 하지만, 한줄에 모든 것이 들어간 이상한 코드를 만들게 되는 경우도 있기에 코딩테스트에서 너무 지나치게 사용할 경우 코드를 수정하기 더 힘들어지게 됩니다.<br/>



컴프리헨션은 10만개 또는 100만개의 데이터를 다룰 때에는 이 데이터를 모두 생성하기 때문에 신경을 쓰지 않았던 공간 복잡도를 생각하게 됩니다. 여기에 대한 대안으로 제너레이터(generator)를 사용하는 것이 권장됩니다.<br/>



### 제너레이터 (generator)

https://www.daleseo.com/python-yield/

list

- list 를 사용하면 모든 결과 값을 메모리에 올려놓은 후 연산을 시작합니다..

yield

- yield 를 사용하면 결과값을 하나씩 메모리에 올려놓으면서 수행합니다.
- yield 는 generator 라고 불리는 파이썬의 개념입니다.
- yield 를 만나면 값을 반환하고 더 이상 진행할 수 없는 상태가 아니라면 next()가 호출되기 전 까지 대기합니다.

<br/>

그리고 yield 는 게으른 반복자 (lazy iterator) 라고도 불립니다. generator 의 lazy iterator 속성을 이용하면 효율적인 프로그램을 작성할 수 있습니다.<br/>

한번에 메모리에 모두 올리기에는 대용량의 데이터일 경우 스트림 방식의 데이터로 처리하게 되는데 이런 경우에 유용하게 사용할 수 있습니다.<br/>



제너레이터를 만드는 방법은 두가지입니다.

- 함수를 정의할 때 return 대신 yield 문구를 넣는 방식
- 컴프리헨션 문구를 소괄호로 감싸주는 방식 (generator comprehension, 제너레이터 컴프리헨션)

<br/>



제너레이터의 장점은 함수가 실행할 것이 많더라도 메모리는 항상 고정된 값을 가진다는 점입니다. 100만개의 데이터를 제곱해서 더해야 할 경우 리스트컴프리헨션가 제너레이터 컴프리헨션의 성능은 메모리 사용량에서 크게 차이를 보입니다. 리스트 컴프리헨션을 사용하면 8MB를 사용하지만 제너레이터를 사용하면 112 Byte 만을 소모합니다.<br/>



다음은 컴프리헨션을 소괄호로 감싼 제너레이터 컴프리헨션으로 데이터를 만드는 예제입니다.

```python
data = (c for c in "ABC")
for d in data:
    print(d)
```

<br/>

출력결과

```plain
A
B
C
```

<br/>



이번에는 1 \~ 10 의 숫자를 제너레이터 컴프리헨션으로 생성하는 예제입니다.

```python
data = (i for i in range(1, 11))

for d in data:
    print(f"d = {d}")
```

<br/>



출력결과

```plain
d = 1
d = 2
d = 3
d = 4
d = 5
d = 6
d = 7
d = 8
d = 9
d = 10
```

<br/>



## generator comprehension

https://www.daleseo.com/python-yield/<br/>

list

- list comprehension 은 `[]` 을 사용합니다.

generator

- generator comprehension 은 `()` 을 사용합니다.

```
print("--- generator comprehension ")
data = (c for c in "ABC")

for d in data:
    print(d)
```



출력결과

```plain
A
B
C
```

<br/>



### 제너레이터 컴프리헨션과 리스트 컴프리헨션 성능 비교

다음은 제너레이터 컴프리헨션을 수행했을 때의 수행시간, 메모리 사이즈와 리스트 컴프리헨션을 사용했을 때의 수행시간, 메모리 사이즈를 비교하는 예제입니다.

```python
from sys import getsizeof
import time

# list comprehension
comprehension = [num ** 2 for num in range(1000000)]
start_time = time.time()
sum_comprehension = sum(comprehension)
print(f"comprehension time = {time.time() - start_time}")
print(f"comprehension memory size = {getsizeof(comprehension)}, (MB) = {getsizeof(comprehension)/1024/1024}MB")

# generator comprehension
generator = (num ** 2 for num in range(1000000))
start_time = time.time()
sum_generator = sum(generator)
print(f"generator time = {time.time() - start_time}")
print(f"generator memory size = {getsizeof(generator)}Byte")
```

<br/>



출력결과는 다음과 같습니다.

```plain
comprehension time = 0.02364325523376465
comprehension memory size = 8448728, (MB) = 8.057334899902344MB
generator time = 0.08455371856689453
generator memory size = 200Byte
```

<br/>



## 가급적이면 "".join()을 사용하자. + 는 지양하자.

문자열을 합칠 때 문자열의 개수가 정말 적은 것이 아니라면 + 연산자를 사용하면 안됩니다. <br/>

파이썬의 문자열은 불변이기에 +로 합칠때 매번 새로운 메모리에 데이터를 복사해서 문자열을 만들기에 사실상 시간복잡도가 O(n^2) 가 됩니다.<br/>

가급적이면 "".join() 을 사용해서 문자열을 합치는 것을 권장합니다. "".join() 은 이렇게 새로운 메모리에 데이터를 복사하는 과정을 거치지 않기에 빠르게 문자열을 합칠 수 있습니다.<br/>

<br/>



## 문자열 → 리스트, 리스트 → 문자열

### 문자열 → 리스트

```python
helloworld1 = "Hello World"
print(helloworld1.split())

helloworld2 = "Hello,World"
print(helloworld2.split(","))

helloworld3 = "HelloWorld"
print(f"{[i for i in helloworld3]}")
```

<br/>

출력결과

```plain
['Hello', 'World']
['Hello', 'World']
['H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd']
```

<br/>



### 리스트 → 문자열

```python
list = ["Hello", "World"]
print("".join(list))
print(" ".join(list))
print(",".join(list))
```

<br/>

출력결과

```plain
HelloWorld
>>> print(" ".join(list))
Hello World
>>> print(",".join(list))
Hello,World
>>>
```





##  슬라이싱

슬라이싱 문법은 아래와 같습니다.

```python
arr[start : end : step]
```

- start : 시작위치 (inclusive)
- end : 종료위치 (exclusive)
- step : step 만큼 더해나간다. step 에는 음수를 지정하는 것 역시 가능하다.

<br/>



문자열 슬라이싱

```python
print('Chocolate'[3::2])
```

<br/>

2 차원, 3차원 배열에도 사용가능합니다.

```python
data = [[(0,1), (2,3), (4,5)], [(6,7), (8,9), (0,1)]]
print(data[1:][0][::2])
```

<br/>



시간 복잡도를 줄이고자 할 때 직접 연산량을 줄일수도 있지만 슬라이싱으로 하나의 변수를 최대한  활용하는 것 역시 도움이 됩니다.<br/>

<br/>



# 표준 라이브러리들

코딩테스트시 필요 기능을 직접 구현할 경우 실행도중에 오류가 있는지, 효율성은 제대로 지켰는지 등등 검증이 많이 필요하게 됩니다. 코딩테스트 시 작성한 코드의 검증할 부분을 최소로 줄여야 시간낭비를 줄일 수 있습니다. 만약 표준 라이브러리를 사용하면 검증할 필요가 있는 부분들이 줄어들게 되어서 이런 문제들을 모두 해결할 수 있습니다.<br/>

코딩테스트에서는 외부 라이브러리를 못쓰게 하지만, 표준 라이브러리는 허용되므로 가급적 표준라이브러리를 최대한 잘 활용하는 것이 좋습니다.<br/>

<br/>



### heapq 

https://www.daleseo.com/python-heapq/

Java 의 ProrityQueue 처럼 우선순위 큐 역할을 하는 파이썬의 우선순위 큐입니다.<br/>

이진트리 기반의 최소힙(min heap) 자료구조로 구성되어 있습니다.<br/>

데이터 insert 시 정렬된 상태로 tree 에 insert 되는 구조입니다.<br/>



주로 우선순위 큐, 최단거리 알고리즘 문제 (e.g. 다익스트라 개념), 그리디 알고리즘 문제 등에 자주 사용되는 라이브러리입니다.<br/>

heapq 모듈을 사용하면 파이썬의 일반 list 를 최소힙(min heap) 처럼 사용할 수 있습니다. (참고로 자바의 `PriorityQueue` 처럼 별도의 자료구조는 아닙니다.)

비어있는 리스트를 생성한 후에 heapq 모듈의 함수를 호출할 때 이 리스트를 넘겨서 heap 자료구조로 생성하는 방식으로 생성합니다.<br/>



사용법 1

```
import heapq

heap = []
heapq.heappush(heap, (0, start))
value, destination = heapq.heappop(heap)
```

<br/>

사용법 2

```
from heapq import heappush, heappop

heap = []
```

<br/>



#### heap 자료구조 생성

```
"""
heap 자료구조 생성
"""
def create_heap():
    heap = []
    heappush(heap, 5)
    heappush(heap, 1)
    heappush(heap, 3)
    heappush(heap, 7)

    print(heap)

print("===")
print("create_heap()")
create_heap()
```



출력

```plain
===
create_heap()
[1, 5, 3, 7]
```



#### 힙에서 원소 제거

```
"""
heap 에서 원소 제거
"""
def remove_element():
    heap = []
    heappush(heap, 5)
    heappush(heap, 7)
    heappush(heap, 3)
    heappush(heap, 1)

    print("removed ", heappop(heap))
    print(heap)

print("\n===")
print("remove_element()")
remove_element()
```



출력

```plain
===
remove_element()
removed  1
[3, 7, 5]
```

<br/>

#### 삭제 없이 원소 얻기 (peek())

```
"""
삭제 없이 원소 얻기 (peek())
"""
def peek_element():
    heap = []
    heappush(heap, 5)
    heappush(heap, 7)
    heappush(heap, 3)
    heappush(heap, 1)

    print("current ", heap[0])
    print(heap)

print("\n===")
print("peek_element()")
peek_element()
```

출력

```plain
===
peek_element()
current  1
[1, 3, 5, 7]
```

<br/>



#### 기존 리스트를 힙으로 변환

```
"""
기존 리스트를 힙으로 변환
"""
def list_heapify():
    list = [7, 3, 5, 1, 9]
    heapify(list)
    print(list)

print("\n===")
print("list_heapify()")
list_heapify()
```

출력결과

```
===
list_heapify()
[1, 3, 5, 7, 9]
```

<br/>



#### 최대힙

```
"""
최대힙
"""
def max_heap():
    list = [9, 3, 1, 7, 5]
    heap = []

    for n in list:
        heappush(heap, (-n, n)) #  (-n, n) 은 (우선순위, 값) 입니다.
    
    while heap:
        print(heappop(heap)[1])

print("\n===")
print("max_heap()")
max_heap()
```

출력결과

```plain
===
max_heap()
9
7
5
3
1
```

<br/>

#### n 번째 최소값/최대값

- 배열을 힙으로 만든 후 heappop() 을 n번 호출
- heapify() 함수로 힙으로 만든 후 heappop() 을 n 번 호출
- nsmallest(), nlargest() 사용



##### 배열을 힙으로 만든 후 heappop() 을 n번 호출

```
"""
n 번째 최소, 최대
- 배열을 힙으로 만든 후 heappop() 을 n번 호출
"""
def find_nth_smallest_by_forloop(nums, n):
    heap = []
    for num in nums:
        heappush(heap, num)
    
    nth_min = None
    for num in range(n):
        nth_min = heappop(heap)
    
    return nth_min

print("\n===")
print("find_nth_smallest_by_forloop >>> ")
print(find_nth_smallest_by_forloop([1,5,3,7,9], 3))
print("\n")
```

출력결과

```plain
===
find_nth_smallest_by_forloop >>>
5
```

<br/>



##### heapify() 함수로 힙으로 만든 후 heappop() 을 n 번 호출

```
def find_nth_smallest_by_heapify(nums, n):
    heapify(nums)

    nth_min = None
    for i in range(n):
        nth_min = heappop(nums)
    
    return nth_min

print("\n===")
print("find_nth_smallest_by_heapify >>> ")
print(find_nth_smallest_by_heapify([1,5,3,7,9], 3))
print("\n")
```



출력결과

```plain
===
find_nth_smallest_by_heapify >>>
5
```

<br/>



##### nsmallest(), nlargest() 사용

nsmallest(), nlargest()

```
"""
n 번째 최소, 최대
- nsmallest(), nlargest() 사용
"""
from heapq import nsmallest
from heapq import nlargest

def find_nth_smallest_by_nsmallest():
    nth_min = nsmallest(3, [1,5,3,7,9])[-1]
    return nth_min
    
print("\n===")
print("find_nth_smallest_by_nsmallest >>> ")
print(find_nth_smallest_by_nsmallest())
print("\n")

def find_nth_largest_by_nlargest():
    nth_max = nlargest(3, [1,5,7,3,9])[-1]
    return nth_max

print("\n===")
print("find_nth_largest_by_nlargest >>> ")
print(find_nth_largest_by_nlargest())
print("\n")
```

<br/>



#### 힙 정렬

어떤 데이터를 insert 하면서 순서에 맞춘 자료로 읽어들일수 있으려면 heappush 를 통해 min-heap 에 데이터를 계속해서 insert 하면 됩니다.

```
def get_sorted_heap(nums):
    heap = []
    for n in nums:
        heappush(heap, n)
    
    sorted_nums = []
    while heap:
        sorted_nums.append(heappop(heap))

    return sorted_nums
print("\n===")
print("get_sorted_heap >>> ")
print(get_sorted_heap([1,5,3,7,9]))
print("\n")
```

출력결과

```plain
===
get_sorted_heap >>>
[1, 3, 5, 7, 9]
```

<br/>



### collections (Counter, deque, defaultdict, OrderedDict, namedtuple, ChainMap)

연속되는 자료구조를 다루는데 사용되는 라이브러리 이며 주로 아래의 클래스들을 자주 사용합니다.

- Counter : 리스트 또는 문자열과 같은 Iterable 한 객체를 받아서 값이 같은 것끼리 묶은 후 카운팅을 하는 클래스입니다.
- deque : 양방향 큐 입니다. 앞, 뒤 양방향에서 엘리먼트를 추가하거나 제거 가능합니다.
- defaultdict : 

이 외에도 아래의 클래스들 역시 있고, 코딩테스트 시 사용하면 좋은 클래스들 입니다.

- OrderedDict : 딕셔너리를 정렬된 상태로 저장되게끔 하는 클래스입니다.
- namedtuple : 자바스크립트 처럼 객체에 이름을 붙여서 저장할 수 있도록 해줍니다.
- ChainMap : 두개의 딕셔너리를 매핑하고 조합해서 보여줍니다.

<br/>



#### import

세부 자료형들을 개별 import 하는 방법은 아래와 같습니다.

```python
from collections import deque
from collections import Counter
from collections import OrderedDict
from collections import defaultdict
from collections import namedtuple
```

<br/>



collections 내의 모든 클래스를 import 하는 방식은 아래와 같습니다. 

```python
import collections
```

<br/>



단 이 경우에는 각각의 class 를 사용 시에 아래와 같이 `collections` 를 붙여서 사용해야 한다는 점은 불편하기에 유념해두셔야 합니다

```python
import collections

count = collections.Counter([1,2,3])
```

<br/>



아래와 같이 한번 감싸는 함수를 만드는 경우를 생각해볼 수도 있는데 가급적이면 첫번째에 소개했던 방식인 `from collections import deque` 을 추천합니다.

```python
import collections

def cnts(nums):
    return collections.Counter(nums)

nums = [1,2,3]
print(cnts(nums))
```

<br/>



#### Counter

문자열 또는 리스트 같은 iterable 한 자료형을 다룰 수 있는 클래스입니다.

```python
from collections import Counter

count_nums = Counter([1,2,3,3,3])
print(f"count = {count_nums}")

count_str = Counter("abccca")
print(f"abccca = {count_str}")
```

<br/>



출력결과

```plain
count = Counter({3: 3, 1: 1, 2: 1})
abccca = Counter({'c': 3, 'a': 2, 'b': 1})
```

<br/>



#### deque

deque 는 stack(스택), queue(큐)를 사용할 때 사용합니다.<br/>

시작점, 끝 등에 값을 넣고 빼는 데에 최적화된 연산을 수행합니다. 대부분의 경우 deque 는 list 보다 최적화된 연산 속도를 제공합니다.<br/>

대부분의 경우 데크(deque)는 리스트(list)보다 좋은 선택이며, 특히 push, pop 이 빈번한 문제를 풀때 list 보다 월등한 속도를 냅니다.<br/>

예를 들어 백준 7576 번 "토마토" 문제를 풀때 list (리스트)를 사용해 익은 토마토를 담도록 코드를 짜면 시간초과로 통과하지 못합니다. 반면 deque(데크)를 사용하면 무난히 통과합니다.<br/>

참고 : [문제 풀이 - 백준 7576](https://jae04099.tistory.com/entry/%EB%B0%B1%EC%A4%80-7576-%ED%86%A0%EB%A7%88%ED%86%A0-%ED%8C%8C%EC%9D%B4%EC%8D%AC-%ED%95%B4%EC%84%A4%ED%8F%AC%ED%95%A8) 

```python
from collections import deque

m, n = map(int, input().split())
matrix = [list(map(int, input().split())) for _ in range(n)]
queue = deque([])
dx, dy = [-1, 1, 0, 0], [0, 0, -1, 1]
res = 0

for i in range(n):
    for j in range(m):
        if matrix[i][j] == 1:
            queue.append([i, j])

def bfs():
    while queue:
        x, y = queue.popleft()
        for i in range(4):
            nx, ny = dx[i] + x, dy[i] + y
            if 0 <= nx < n and 0 <= ny < m and matrix[nx][ny] == 0:
                matrix[nx][ny] = matrix[x][y] + 1
                queue.append([nx, ny])

bfs()
for i in matrix:
    for j in i:
        if j == 0:
            print(-1)
            exit(0)
    res = max(res, max(i))
print(res - 1)
```

<br/>



#### defaultdict

참고 : [파이썬 dictionary 의 기본값 처리](https://www.daleseo.com/python-collections-defaultdict/)<br/>



##### 기본값으로 숫자 기본값 지정

```python
from collections import defaultdict 

def count_letter(input):
    counter = defaultdict(int)
    # 또는 
    # counter = defaultdict(lambda: 0)
    for c in input:
        counter[c] += 1
    return counter

result = count_letter("abcde")
print(f"result = {result}")
print(f"result['z'] = {result['z']}")
```

defaultdict 클래스의 생성자로 int 함수를 넘긴 코드입니다. int() 는 0 을 리턴하기에 int 함수를 defaultdict 클래스의 생성자로 넘겨줬습니다. 람다함수를 사용하면 `counter = defaultdict(lambda: 0)` 을 넘겨도 동일하게 동작합니다.<br/>

<br/>



출력결과

```plain
result = defaultdict(<class 'int'>, {'a': 1, 'b': 1, 'c': 1, 'd': 1, 'e': 1})
result['z'] = 0
```

<br/>



##### 기본값으로 list 기본값 지정

```python
from collections import defaultdict 

def group_by_len(input):
    counter = defaultdict(list)
    for word in input:
        l = len(word)
        counter[l].append(word) 
    return counter

result = group_by_len(["aa","bb","aaa", "bbb", "aaaa", "bbbb"])
print(f"result = {result}")
print(f"result['zzz'] = {result['zzz']}")
```

<br/>



출력결과

```plain
result = defaultdict(<class 'list'>, {2: ['aa', 'bb'], 3: ['aaa', 'bbb'], 4: ['aaaa', 'bbbb']})
result['zzz'] = []
```

<br/>



##### 기본값으로 set 기본값 지정

```python
from collections import defaultdict 

def group_set_by_len(input):
    counter = defaultdict(set)
    for word in input:
        l = len(word)
        counter[l].add(word) 
    return counter

result = group_set_by_len(["aa","bb", "aa", "aaa", "bbb", "bbb", "aaaa", "bbbb"])
print(f"result = {result}")
print(f"result['zzz'] = {result['zzz']}")
```

<br/>



출력결과

```plain
result = defaultdict(<class 'set'>, {2: {'bb', 'aa'}, 3: {'bbb', 'aaa'}, 4: {'bbbb', 'aaaa'}})
result['zzz'] = set()
```

<br/>



### itertools

경우의 수 문제에 사용하며, 순열(permutation), 조합(combination), 중복순열(permutations_with_replacement), 중복조합(combinations_with_replacement) 에 사용합니다.<br/>



### math

최대공약수, 최소공배수, 팩토리얼, 제곱근, 로그, 파이를 계산시에 사용합니다. 파이, 자연상수 같은 상수도 math 라이브러리를 이용하면 됩니다.<br/>



### bisect

이진 탐색기능을 제공합니다. <br/>

이 글을 읽는 분들은 대부분 아시겠지만, 이진 탐색은 정렬된 자료구조에서만 정확하게 동작을 합니다.<br/>

<br/>



## 문자열

### isalpha(), isnumeric(), isdigit()

#### isalpha() - 모든 원소가 문자일 경우 True, 아니면 False

```python
st = "A"

print(f"st.isalpha() = {st.isalpha()}")
```

<br/>



출력결과

```plain
st.isalpha() = True
```



#### isnumeric()  - 모든 문자열이 숫자로만 이뤄져 있으면 True, 아니면 False

참고) isnumeric(), isdigit() 은 '2³' 과 같은 문자처럼거듭제곱 꼴의 숫자도 숫자로 인식하지만, isdecimal() 은 거듭제곱 꼴의 문자를 숫자가 아닌 문자로 인식합니다.<br/>

#### isdigit() - 모든 원소가 정수일 경우 True, 아니면 False

참고) isnumeric(), isdigit() 은 '2³' 과 같은 문자처럼거듭제곱 꼴의 숫자도 숫자로 인식하지만, isdecimal() 은 거듭제곱 꼴의 문자를 숫자가 아닌 문자로 인식합니다.<br/>

#### isdecimal() - 모든 원소가 숫자일 경우 True, 아니면 False

참고) isnumeric(), isdigit() 은 '2³' 과 같은 문자처럼거듭제곱 꼴의 숫자도 숫자로 인식하지만, isdecimal() 은 거듭제곱 꼴의 문자를 숫자가 아닌 문자로 인식합니다.<br/>

#### isalnum() - 문자열이 영어, 한글,숫자로만 되어있으면 True, 아니면 False



### ord() - 알파벳을 유니코드 정수로 반환

> 아스키코드와 알파벳을 이어주는 함수로 착각하기 쉽지만, 유니코드까지 지원되는 함수라고 합니다.
>
> - 참고 : [Python ord(), chr() functions](https://www.digitalocean.com/community/tutorials/python-ord-chr)



e.g.

```python
str = "A"
print(f"ord(str) = {ord(str)}")
```

<br/>



출력결과

```plain
ord(str) = 65
```

<br/>



### chr() - 유니코드 정수값을 문자로 반환

> 아스키코드와 알파벳을 이어주는 함수로 착각하기 쉽지만, 유니코드까지 지원되는 함수라고 합니다.
>
> - [Python ord(), chr() functions](https://www.digitalocean.com/community/tutorials/python-ord-chr)



e.g.

```python
num = 65
print(f"chr(num) = {chr(num)}")
```

<br/>



출력결과

```plain
chr(num) = A
```

<br/>



### int(str) - 문자열을 숫자로 변환

```python
st = "123"
list = []
for num in st:
    list.append(int(num))

print(f"list = {list}")
```

<br/>



출력결과

```plain
list = [1, 2, 3]
```

<br/>



# 입출력

백준온라인을 풀때 말고는 입출력함수를 쓸일은 없어서 가장 마지막에 정리했습니다.<br/>

## readline()

참고자료 :https://wikidocs.net/26<br/>



데이터를 입력받아야 하는 경우 가급적 sys 모듈의 readline() 이 효율적입니다.<br/>

input() 이라는 함수를 사용할수도 있겠지만, input() 보다는 readline() 이 효율적입니다.

```python
import sys
data = sys.stdin.readline()
```

<br/>



먼저 3085.dat 이라는 파일이 현재 같은 디렉터리 내에 있고 그 내용은 다음과 같습니다.<br/>

```plain
3
CCP
CCP
PPC
```

<br/>



이 것을 읽어들이는 것은 다음과 같이 할 수 있습니다.

```python
f = open("./3085.dat", "r")

while True:
    line = f.readline()
    if not line: break
    print(line)

f.close()
```

<br/>



이렇게 하면 출력결과가 다음과 같이 개행문자와 함게 나타납니다.

```plain
3

CCP

CCP

PPC
```

<br/>



만약 개행 문자(`\n`)를 제거하고 읽어들이고 싶다면 다음과 같이 strip() 함수를 사용해서 코드를 작성하면 됩니다.

```python
f = open("./3085.dat", "r")

while True:
    line = f.readline()
    if not line: break
    line = line.strip()
    print(line)

f.close()
```

<br/>



## read()

3085.dat 이라는 파일이 현재 같은 디렉터리 내에 있고 그 내용은 다음과 같습니다.<br/>

```plain
3
CCP
CCP
PPC
```

<br/>



read() 함수는 파일의 전체내용을 문자열로 리턴합니다.

```python
f = open("./3085.dat", "r")

all_str = f.read()
print(f"{all_str}")

f.close()
```

<br/>



출력결과

```plain
3
CCP
CCP
PPC
```



