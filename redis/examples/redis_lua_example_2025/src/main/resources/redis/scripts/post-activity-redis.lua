local cat_id = tonumber(ARGV[1])
local cat_name = ARGV[2]
local cat_limit = tonumber(ARGV[3])

local key_set = KEYS[1]
local key_list = KEYS[2]

if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
    return 'DUPLICATED_ENQUEUE_REQUEST'
end

if cat_limit > redis.call('SCARD', KEYS[1]) then
    redis.call('SADD', key_set, cat_id)
    local value = cat_name
    redis.call('RPUSH', key_list, value)
    return 'SUCCESS'
end

return 'CAT_TRY_AGAIN'