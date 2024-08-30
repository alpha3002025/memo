

## 코딩테스트로 파이썬 언어 사용시 꿀팁

### Java 등 다른 언어의 스타일로 파이썬 코드를 작성하지 말자

Java 등에 존재하는 getter/setter 로 값을 받아오고 접근하는 기능을 python 에서도 똑같이 작성할 경우 개발 효율이 떨어집니다. 기본적으로 python 은 공개 속성이기 때문에 그럴 필요가 없고 이렇게 할 경우 성능적인 문제를 가지게 됩니다. 만약 getter/setter가 필요하면 `@property` 데코레이터를 사용하시는 것을 권장합니다.<br/>

<br/>





## 시간복잡도 줄이기

### readline() : 입력 속도를 빠르게 

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

(리스트 컴프리헨션 vs 제너레이터 : 편의성과 효율의 대결)<br/>

<br/>



## 가급적이면 "".join()을 사용하자. + 는 지양하자.

문자열을 합칠 때 문자열의 개수가 정말 적은 것이 아니라면 + 연산자를 사용하면 안됩니다. <br/>

파이썬의 문자열은 불변이기에 +로 합칠때 매번 새로운 메모리에 데이터를 복사해서 문자열을 만들기에 사실상 시간복잡도가 O(n^2) 가 됩니다.<br/>

가급적이면 "".join() 을 사용해서 문자열을 합치는 것을 권장합니다. "".join() 은 이렇게 새로운 메모리에 데이터를 복사하는 과정을 거치지 않기에 빠르게 문자열을 합칠 수 있습니다.<br/>

<br/>



### 슬라이싱

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



## 표준 라이브러리들

코딩테스트시 필요 기능을 직접 구현할 경우 실행도중에 오류가 있는지, 효율성은 제대로 지켰는지 등등 검증이 많이 필요하게 됩니다. 코딩테스트 시 작성한 코드의 검증할 부분을 최소로 줄여야 시간낭비를 줄일 수 있습니다. 만약 표준 라이브러리를 사용하면 검증할 필요가 있는 부분들이 줄어들게 되어서 이런 문제들을 모두 해결할 수 있습니다.<br/>

코딩테스트에서는 외부 라이브러리를 못쓰게 하지만, 표준 라이브러리는 허용되므로 가급적 표준라이브러리를 최대한 잘 활용하는 것이 좋습니다.<br/>

<br/>



### heapq 

이진 트리 기반의 최소 힙(min heap) 자료구조입니다.<br/>

우선순위 큐, 최단거리 알고리즘 문제 (e.g. 다익스트라 개념), 그리디 알고리즘 문제 등에 자주 사용되는 라이브러리입니다.<br/>

데이터 insert 시 정렬된 상태로 tree 에 insert 되는 구조입니다.<br/>

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







