package com.demo.components.redis.cluster;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Redis缓存
 *
 * @Author ddmc
 * @Create 2019-04-25 15:55
 */
@Service
@RequiredArgsConstructor
public class CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    // 加锁lua脚本, 超时时间单位: 毫秒
    private String lockScript = "local key = KEYS[1] " +
            "local value = ARGV[1] " +
            "local expire_millis = tonumber(ARGV[2])" +
            "if redis.call(\"set\", key, value, \"PX\", expire_millis, \"NX\") then " +
            " return \"1\" " +
            "else " +
            " return \"0\"" +
            "end";
    // 单位：秒
    private String lockLua = "local lock_key = KEYS[1] " +
            "local lock_value = ARGV[1] " +
            "local expire_seconds = tonumber(ARGV[2]) " +
            "if redis.call(\"SETNX\", lock_key, lock_value) == 1 then" +
            " redis.call(\"EXPIRE\", lock_key, expire_seconds) " +
            " return \"1\" " +
            "else " +
            " return \"0\" " +
            "end";
    private String lockReleaseScript = "if redis.call(\"GET\", KEYS[1]) == ARGV[1] then " +
            "redis.call(\"DEL\", KEYS[1]) " +
            " return \"1\" " +
            "else " +
            " return \"0\" " +
            "end";

    private final StringRedisTemplate stringRedisTemplate;


    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public boolean tryLock(String key, String value, long expiredMillis) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(lockScript, String.class);
        String result = getStringRedisTemplate().execute(redisScript, Collections.singletonList(key), value, String.valueOf(expiredMillis));
        return "1".equals(result);
    }

    public boolean releaseLock(String key, String value) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(lockReleaseScript, String.class);
        String result = getStringRedisTemplate().execute(redisScript, Collections.singletonList(key), value);
        return "1".equals(result);
    }

    public long bitCount(final String key) {
        return getStringRedisTemplate().execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, int start, int end) {
        return getStringRedisTemplate().execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return getStringRedisTemplate().execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }

}