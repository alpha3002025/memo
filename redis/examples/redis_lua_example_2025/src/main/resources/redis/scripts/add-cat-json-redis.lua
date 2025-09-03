local json_value = ARGV[1]

local json_value_list_key = KEYS[1]

redis.call('RPUSH', json_value_list_key, json_value)

return 'SUCCESS'