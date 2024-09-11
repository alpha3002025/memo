

rows = 3
columns = 3


matrix = [[i*columns + (k+1) for k in range(columns)] for i in range(rows)]

print(matrix)


