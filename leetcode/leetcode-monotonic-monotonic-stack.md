## leetcode - Monotonic, Monotonic Stack

풀어볼 문제 리스트

Monotonic Stack

- Daily Temperatures
  - Leetcode : [Daily temperatures](https://leetcode.com/problems/daily-temperatures/description/)
  - Neetcode : [Monotonic Stack - Daily Temperatures - Leetcode 739](https://youtu.be/cTBiBSnjO3c)

- Building With an Ocean View
  - https://leetcode.com/problems/buildings-with-an-ocean-view/
- Largest Rectangle in Histogram
  - Leetcode : [Largest Rectangle In Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/)
  - Neetcode : [Largest Rectangle In Histogram - Leetcode 84](https://youtu.be/zx5Sw9130L0)
- 주식가격
  - [코딩테스트 연습 \> 스택/큐 \> 주식가격](https://school.programmers.co.kr/learn/courses/30/lessons/42584)





## 개념정리

### Monotonic Stack, 단조 스택이란?

https://iridescentboy.tistory.com/146



- Monotonic Stack 은 원소가 Increasing/Decreasing 으로 정렬되어 있는 배열을 의미한다.
- Monotonic Stack 그 자체로 유용함은 없지만, 정렬되어 있지 않은 배열을 Monotonic Stack 으로 만드는 과정 혹은 Monotonic Stack 에 새로운 원소가 입력되었을 때 정렬하는 과정에서 발생하는 정보들이 유용하다.
- 대표적인 예제는 Find Next Greatest Number 다. Brute Force 로 풀면 O^2 이므로 입력이 많아지면 좋지 Monotonic Stack 으로 풀면 O(n) 에 풀수 있다.
- e.g. 
  - Daily Temperatures (Leetcode 739)
  - Building with an Ocean View (LeetCode 1762)
  - Largest Rectangle in Histogram (LeetCode 84)
  - (프로그래머스) 주식가격



### increasing, decreasing

https://velog.io/@soopsaram/Monotonic-Stack-%EB%AC%B8%EC%A0%9C

increasing

- 삽입하려는 값이 stack 의 top 보다 클 때만 push. 삽입하려는 값 보다 작은 값은 모두 stack 에서 pop.
- 배열에서 다음 작은 값이 무엇인지 알아낼 수 있다.

decreasing

- 삽입하려는 값이 stack 의 top 보다 작을 때만 push. 삽입하려는 값보다 큰 값은 모두 stack 에서 pop
- 배열에서 다음 큰 값이 무엇인지 알 수 있다.