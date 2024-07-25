이번 주(2024.07.22 \~ 2024.07.29) 풀 문제 모음

- https://leetcode.com/problems/group-anagrams/
- https://leetcode.com/problems/valid-parentheses/
- https://leetcode.com/problems/reorganize-string/description/
- https://leetcode.com/problems/generate-parentheses/description/

<br/>



leetcode/string

- https://leetcode.com/tag/string/

<br/>



파이썬 문법 정리 필요한 내용들

- [파이썬의 ord 함수](https://blockdmask.tistory.com/544)

<br/>



## [49. Group Anagrams](https://leetcode.com/problems/group-anagrams/) (Medium)

### Link

- https://leetcode.com/problems/group-anagrams/



### 요약

python 은 튜플을 dict 의 key 로 사용할 수 있다. 그래서 각 단어에서 문자열의 출현 횟수 카운팅 결과값을 키로 하고 이 키에 대해서는 해당 문자열들의 리스트를 값으로 지정하는 dict 를 생성하는 방식으로 애너그램을 만들어낸다.<br/>

애너그램은 1차 접근은 '해시' 다. 하는 생각을 가지고 있을 것!!<br/>

\[tuple: ["문자열1", "문자열2"]\] 의 실제 형식은 아래와 같다.

```plain
defaultdict(<class 'list'>, {(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['eat']})
defaultdict(<class 'list'>, {(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['eat', 'tea']})
defaultdict(<class 'list'>, {(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['eat', 'tea'], (1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['tan']})
defaultdict(<class 'list'>, {(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['eat', 'tea', 'ate'], (1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['tan']})
defaultdict(<class 'list'>, {(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['eat', 'tea', 'ate'], (1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['tan', 'nat']})
defaultdict(<class 'list'>, {(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['eat', 'tea', 'ate'], (1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['tan', 'nat'], (1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0): ['bat']})
```

<br/>



### 문제

Given an array of strings `strs`, group **the anagrams** together. You can return the answer in **any order**.<br/>

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.<br/>

<br/>

**Example 1:**

```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Example 2:**

```
Input: strs = [""]
Output: [[""]]
```

**Example 3:**

```
Input: strs = ["a"]
Output: [["a"]]
```

<br/>



### 풀이

```python
class Solution:
    def groupAnagrams(self, strs: List[str]) -> List[List[str]]:
        ans: DefaultDict[int, List[str]] = collections.defaultdict(list)
        for s in strs:
            count = [0] * 26
            for c in s:
                count[ord(c) - ord("a")] += 1
            ans[tuple(count)].append(s)
        return ans.values()
```



## [767. Reorganize String](https://leetcode.com/problems/reorganize-string/) (Medium)

### Link

- https://leetcode.com/problems/reorganize-string

<br/>



### 요약

- (1) : 각 문자 당 출현 횟수를 카운팅한다.
- (2) : (1) 에서 구한 카운팅인 char\_counts 에서 최대 출현 횟수, 최대 출현 문자를 구한다.
- (3) : 최대 갯수가 문자열의 전체 크기 / 2 보다 크다면 더 시도해볼 필요가 없다. 
  - 절반이상을 최대출현 문자가 차지하고 있기에 블가능한 케이스다.
- (4) : 최대 출현 문자(=letter)를 index = 0 부터 한 칸 건너 하나씩(index += 2) 배치
- (5) : 나머지 문자들 각각을 index = 1 부터 한 칸 건너 하나씩 (index += 2) 배치

<br/>



### 문제

Given a string `s`, rearrange the characters of `s` so that any two adjacent characters are not the same.

Return *any possible rearrangement of* `s` *or return* `""` *if not possible*.

 

**Example 1:**

```
Input: s = "aab"
Output: "aba"
```

**Example 2:**

```
Input: s = "aaab"
Output: ""
```

 

**Constraints:**

- `1 <= s.length <= 500`
- `s` consists of lowercase English letters.

<br/>



### 풀이

```python
class Solution:
    def reorganizeString(self, s: str) -> str:
        ## 각 문자 당 출현 횟수를 카운팅한다. 
        char_counts = Counter(s)
        max_count, letter = 0, ''

        ## 최대 출현 횟수, 최대 출현 문자를 char_counts 에서 구한다.
        for char, count in char_counts.items():
            if count > max_count:
                max_count = count
                letter = char

        ## 최대 갯수가 문자열의 전체크기 / 2 보다 크다면 더 시도해볼 필요가 없다.
        if max_count > (len(s) + 1) // 2: 
            return ""
        
        ans = [''] * len(s)
        index = 0

        # letter : most frequent letter
        # 가장 흔히 출현하는 문자(=letter)를 한칸 건너 하나씩(index += 2) 배치
        while char_counts[letter] != 0:
            ans[index] = letter
            index += 2
            char_counts[letter] -= 1
        
        # 나머지 문자들을 index = 1 에서부터 한칸 건너 하나씩(index += 2) 배치
        for char, count in char_counts.items():
            while count > 0:
                if index >= len(s): # index 가 문자열의 길이를 넘어서면 1로 초기화
                                    # 1 부터 시작하는 이유는 위에서 most frequent letter 를 
                                    # 0 부터 배치했기에 그 다음 위치인 1 부터 시작하는 것임
                    index = 1
                
                # count 횟수만큼 index 위치마다 char 를 지정해준다
                ans[index] = char
                index += 2
                count -= 1
        
        return ''.join(ans)
```

<br/>



## [22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) (Medium) / (1) Recursive

- 어제 회사에서 일하기 전에 문제 읽고 일할 때 일하다말고 힘빠졌을 때 틈틈히 풀이법을 떠올리면서 하루를 보내다가 근무시간 끝난 후 10분동안 문제 풀때 풀렸다.

<br/>



### Link 

- https://leetcode.com/problems/generate-parentheses/

<br/>



### 요약

- list 를 stack 처럼 사용해서 가장 마지막 요소를 추가(append)하거나 빼는(pop) 방식으로 연산을 한다.

- 리스트가 완성되면 그 리스트는 문자열로 바꾼 후 ans = \[\] 에 넣어준다.
- list 는 acc(누적, accumulate)하는 역할로 사용했다.
- 항상 그렇듯 재귀 돌릴때 나는 `acc` 변수를 사용하면 풀린다.
- 재귀 시에 의사 결정 트리를 만들 때 아래의 구문이 정말 많이 쓰인다.
  - acc.append("(")
  - recur (acc, left + 1, right)
  - acc.pop()

<br/>



### 문제

Given `n` pairs of parentheses, write a function to *generate all combinations of well-formed parentheses*.

 

**Example 1:**

```
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
```

**Example 2:**

```
Input: n = 1
Output: ["()"]
```

 

**Constraints:**

- `1 <= n <= 8`

<br/>



### 풀이

```python
class Solution:
    def generateParenthesis(self, n: int) -> List[str]:
        ans = []

        def recur(acc, left, right):
            if n*2 == len(acc):
                ans.append("".join(acc))
            
            if left < n:
                acc.append("(")
                recur(acc, left+1, right)
                acc.pop()

            if right < left:
                acc.append(")")
                recur(acc, left, right+1)
                acc.pop()

        recur([], 0, 0)

        return ans
```

<br/>



## [22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) (Medium) / (2) Divide and Conquer

### Link

<br/>



### 요약

<br/>



### 문제

<br/>



### 풀이

<br/>











