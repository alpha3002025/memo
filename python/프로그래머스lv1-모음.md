# 프로그래머스 lv1 모음

# 파이썬의 기본 연산자

## 정수 제곱근 판별 (제곱, 제곱근 연산자)

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12934

- 제곱 : `a ** 2`

- 제곱근 : `a ** (1/2)` 

```python
def solution(n):
    num = n**(1/2)
    if(int(n**(1/2)) == num):
        return (num+1)**2
    else:
        return -1
```

<br/>



## 짝수와 홀수 (나머지 연산자)

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12937

```python
def solution(num):
    if num %2 == 1:
        return "Odd"
    else :
        return "Even"
```

<br/>



## 히샤드 수

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12947

```python
def solution(x):
    num = str(x)
    sum = 0
    
    for n in num:
        sum += int(n)
        
    if x % sum == 0:
        return True
    return False
```





# 문자열, slice 문법

## 핸드폰 번호 가리기 (slice 문법)

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12948?language=python3

```pyhton
def solution(phone_number):
    return '*' * len(phone_number[:-4]) + phone_number[-4:]
```

<br/>



## 자릿수 더하기 - int() 활용

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12931

```python
def solution(n):
    s = str(n)
    answer = 0
    
    for num in s:
        answer += int(num)
    
    return answer
```

<br/>



## 이상한 문자 만들기 - upper(), lower() 

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12930

```python
def solution(s):
    pos = 0
    list = []
    
    for a in s:
        if a.isalpha():
            if pos % 2 == 0:
                list.append(a.upper())
            else:
                list.append(a.lower())
            pos += 1
        else:
            list.append(' ')
            pos = 0    
    
    return ''.join(list)
```

<br/>



## 자연수 뒤집어 배열로 만들기 - 정렬이 아닌 역순 슬라이싱을 해야 하는 문제

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12932

정렬을 할 경우 시간복잡도에 걸려서 통과를 못한다.<br/>

문자열로 바꿔서 split() 을 통해서 배열로 바꾼후에 정렬을 `reverse = True` 를 통해 역순정렬을 할수도 있는데 이렇게 하면 nlogn 이 걸리기에 시간복잡도에서 실패<br/>

그냥 뒤에서 부터 읽으면 되는데, 간단한 슬라이싱 문법으로 가능하다.<br/>

- `a[::--1]`

<br/>



```python
def solution(n):
    answer = []
    n = str(n)
    for num in n[::-1]:
        answer.append(int(num))
    return answer
```

<br/>



## 시저암호 - chr(), ord()

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12926

개인적으로는 `list.append(chr(ord(e) + n))` 으로 해야하는데  ord() 대신 int() 를 사용하다가 `list.append(chr(int(e) + n))` 라는 코드를 작성해서 [ValueError: invalid literal for int() with base 10](https://stackoverflow.com/questions/1841565/valueerror-invalid-literal-for-int-with-base-10) 라는 에러르 만났었다. 주의!!!

```python
def solution(s, n):
    
    list = []
    for e in s:
        if e == ' ':
            list.append(' ')
            
        elif 'A' <= e <= 'Z':
            if ord(e) + n > ord('Z'):
                list.append(chr(ord('A') + ord(e) + n - ord('Z') -1))
            else : 
                list.append(chr(ord(e) + n))
        elif  'a' <= e <= 'z':
            if ord(e) + n> ord('z'):
                list.append(chr(ord('a') + ord(e) + n - ord('z') -1))
            else : 
                list.append(chr(ord(e) + n))
    
    return ''.join(list)
```

<br/>





# 그리디

## 최소 직사각형

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/86491

```python
def solution(sizes):
    max_width, max_height = 0, 0
    for size in sizes:
        size.sort() ## 작은 쪽을 width 로 지정
        
        max_width = max(max_width, size[0])
        max_height = max(max_height, size[1])
        
    return max_width * max_height
```

<br/>







# 반복문, 컴프리헨션

## 부족한 금액 계산하기

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/82612

```python
def solution(price, money, count):
    cost = 0
    
    for n in range(1, count+1):
        cost = cost + n * price
    
    if cost > money:
        return cost - money
    
    return 0
```

<br/>



## 같은 숫자는 싫어

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12906

```python
def solution(arr):
    answer = []
    num = -1
    
    for i in arr:
        if num == i:
            continue
        num = i
        answer.append(num)
        
    return answer
```

<br/>



## 콜라츠 추측

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12943

```python
def solution(num):
    flag = True
    cnt = 0
    while flag:
        if num == 1:
            flag = True
            break
            
        if num % 2 == 0:
            num /= 2
        else:
            num *= 3
            num += 1
        cnt+=1
        
        if cnt >= 500 and num > 1:
            return -1
            
    return cnt
```

<br/>



## x 만큼 간격이 있는 n개의 숫자

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12954

여러가지 풀이법이 있는데, python 의 여러가지 문법을 연습해보기 위해서 여러가지 풀이법을 정리해봤다.

```python
def solution(x, n):
    flag = True
    answer = []
    num = x
    answer.append(num)
    while len(answer) < n:
        num += x
        answer.append(num)
    
    return answer
```

<br/>

리스트컴프리헨션

```python
def solution(x, n):
    
    if x == 0:
        return [0]*n
    elif x > 0:
        return [num for num in range(x, x*n + 1, x)]
    else:
        return [num for num in range(x, x*n -1, x)]
```

<br/>



## 직사각형 별 찍기

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12969

```python
a, b = map(int, input().strip().split(' '))
for i in range(1, b+1, 1):
    list = []
    for k in range(1, a+1, 1):
        list.append("*")
    stars = "".join(list)
    print(stars)
```

<br/>



# 정렬

### 문자열 내림차순으로 정렬하기 - 역순 정렬

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12917?language=python3

```python
def solution(s):
    list = [str for str in s]
    list.sort(reverse = True)
    return "".join(list)
```

<br/>



# 배열

### 나누어 떨어지는 숫자 배열

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12910

```python
def solution(arr, divisor):
    answer = [i for i in arr if i%divisor == 0]
    if not answer:
        return [-1]
    return sorted(answer)
```

<br/>

