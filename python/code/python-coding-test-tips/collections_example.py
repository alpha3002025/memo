from collections import Counter
from collections import deque

# count_nums = Counter([1,2,3,3,3])
# print(f"count = {count_nums}")

# count_str = Counter("abccca")
# print(f"abccca = {count_str}")


# deque
# stack = deque()
# for i in range(1, 4):
#     print(stack.append(i))

# print("current stack = ", stack)

# print(stack.pop())
# print(stack.pop())
# print(stack.pop())


# defaultdict

# from collections import defaultdict 

# def count_letter(input):
#     counter = defaultdict(int)
#     for c in input:
#         counter[c] += 1
#     return counter

# result = count_letter("abcde")
# print(f"result 1 = {result}")
# print(f"result['z'] = {result['z']}")



# from collections import defaultdict 

# def group_by_len(input):
#     counter = defaultdict(list)
#     for word in input:
#         l = len(word)
#         counter[l].append(word) 
#     return counter

# result = group_by_len(["aa","bb","aaa", "bbb", "aaaa", "bbbb"])
# print(f"result = {result}")
# print(f"result['zzz'] = {result['zzz']}")



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



