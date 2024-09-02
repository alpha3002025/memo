# 배열

# 교점에 별 만들기





# 행렬 테두리 회전하기

링크 : https://school.programmers.co.kr/learn/courses/30/lessons/77485

풀이방식은 2가지 풀이방법이 있다.<br/>

항상 느끼는 거지만, 일단 시작하는건 쉬운데, 시작하기 전에 어렵지 않은 방향을 미리 생각해둬서 문제풀이 방향을 결정하는건 어렵다. 문제풀이가 쉬운방향으로 결정하는게 어렵다구. 노력해야한다구.<br/>



왼쪽 → 아래 → 오른쪽 → 위 

```python
def rotate(x1, y1, x2, y2, matrix):
    first = matrix[x1][y1]
    min_value = first
    
    # 왼쪽
    for k in range(x1, x2):
        matrix[k][y1] = matrix[k+1][y1]
        min_value = min(min_value, matrix[k+1][y1])
    
    # 아래
    for k in range(y1, y2):
        matrix[x2][k] = matrix[x2][k+1]
        min_value = min(min_value, matrix[x2][k+1])
        
    # 오른쪽
    for k in range(x2, x1, -1):
        matrix[k][y2] = matrix[k-1][y2]
        min_value = min(min_value, matrix[k-1][y2])
    
    # 위
    for k in range(y2, y1 + 1, -1):
        matrix[x1][k] = matrix[x1][k-1]
        min_value = min(min_value, matrix[x1][k-1])
    
    matrix[x1][y1+1] = first
    return min_value

def solution(rows, columns, queries):
    matrix = [[(i) * columns + (k+1) for k in range(columns)] for i in range(rows)]
    result = []
    for x1, y1, x2, y2 in queries:
        result.append(rotate(x1-1, y1-1,x2-1,y2-1,matrix))
    
    return result
```

<br/>



슬라이싱을 이용해 최적화

```python
def solution(rows, columns, queries):
    answer = []
    board = [[columns * k + (i+1) for i in range(columns)] for k in range(rows)]
    for query in queries:
        a,b,c,d = query[0]-1, query[1]-1, query[2]-1, query[3]-1
        row1, row2 = board[a][b:d], board[c][b+1:d+1]
        _min = min(row1 + row2)
        
        for i in range(c, a, -1):
            board[i][d] = board[i-1][d]
            
            if board[i][d] < _min: _min = board[i][d]
        
        for i in range(a,c):
            board[i][b] = board[i+1][b]
            if board[i][b] < _min: _min = board[i][b]
        
        board[a][b+1:d+1], board[c][b:d] = row1, row2
        
        answer.append(_min)
    return answer
```











