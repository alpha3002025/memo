local key_list = KEYS[1]
local cat_id = ARGV[1]

redis.call('RPUSH', key_list, cat_id)
