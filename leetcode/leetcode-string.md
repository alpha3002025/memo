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



## [49. Group Anagrams](https://leetcode.com/problems/group-anagrams/)

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

