local lock_key = KEYS[1]
local lock_value = ARGV[1]
local lock_timeout = tonumber(ARGV[2])
if redis.call("SETNX", lock_key, lock_value) == 1 then
 redis.call("EXPIRE", lock_key, lock_timeout)
 return "1"
else
 return "0"
end
