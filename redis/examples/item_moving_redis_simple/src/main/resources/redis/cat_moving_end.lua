local key_list = KEYS[1]
local key_backup = KEYS[2]

local direction = ARGV[1]
local request_arr = {}

-- request arr 구성
for i=2, #ARGV do
    request_arr[i-1] = ARGV[i]
end

-- 아래 방향으로 이동일때
if(direction == "DOWN") then
    for i=1, #request_arr do
        e1 = redis.call('LREM', key_list, 1, request_arr[i])
        b1 = redis.call('RPUSH', key_backup, e1)
        insert_value = redis.call('LPOP', key_backup)
        redis.call('RPUSH', key_list, insert_value)
    end
else
    for i=1, #request_arr do
        e1 = redis.call('LREM', key_list, 1, request_arr[i])
        b1 = redis.call('RPUSH', key_backup, e1)
        insert_value = redis.call('RPOP', key_backup)
        redis.call('LPUSH', key_list, insert_value)
    end
end
