local key = KEYS[1]
local value = ARGV[1]
local expire_time = tonumber(ARGV[2])

if redis.call("set", key, value, "PX", expire_time, "NX") then
    return "1"
else
    return "0"
end