# EASY



# BOJ 1343 - 폴리오미노

https://www.acmicpc.net/problem/1343

```python
q = input()

answer = ""
count = 0

possible = True
for i in range(len(q)):
  if q[i] == "X":
    count+=1
    if count == 4:
      answer += "AAAA"
      count = 0 # X 개수를 모아서 count 가 4 가 되었을 때 다시 0 으로 초기화
  else: # . 이 나타난 경우 지금까지의 카운트를 기반으로 
        # X 가 몇번나타났는지를 기반으로 판단을 수행
    if count == 0:
      answer += "."

    elif count == 1: # 홀수인 채로 X의 개수가 끝난다면 불가능한 문제
      possible = False
      break
    
    elif count == 2: # X의 개수가 2개 나타났었다면 "BB" 를 추가 후 
                     # 마지막을 "."으로 초기화 
      answer += "BB" 
      count = 0
      answer += "."
    
    elif count == 3:
      possible = False
      break

# 마지막 남은 작대기
if count == 1: # 홀수인채로 끝나면 풀수 없는 문제
  possible = False
elif count == 2:
  answer += "BB"
elif count == 3:
  possible = False

if possible:
  print(answer)
else:
  print(-1)

```

<br/>

