local couponKey = ARGV[2]
local userId = ARGV[1]
local couponLimit = tonumber(ARGV[3])
if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
    return 'DUPLICATED_ENQUEUE_REQUEST'
end

if couponLimit > redis.call('SCARD', KEYS[1]) then
    redis.call('SADD', KEYS[1], userId)
    local couponCnt = tostring(redis.call('SCARD', KEYS[1]))
    local value = tostring(couponKey) + "##" + tostring(userId) + "##" + couponCnt
    redis.call('RPUSH', KEYS[2], value)
    return 'SUCCESS'
end

return 'EXCEEDED_TOTAL_COUPON_ISSUE_LIMIT'