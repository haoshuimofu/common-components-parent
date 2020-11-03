package com.demo.components.redis.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存
 *
 * @Author ddmc
 * @Create 2019-04-25 15:55
 */
@Service
public class CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    /**
     * 锁等待-线程sleep毫秒数
     */
    private final static long LOCK_WAIT_THREAD_SLEEP_MILLS = 100;

    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final Long RELEASE_LOCK_SUCCESS_RESULT = 1L;

    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public CacheManager(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        Assert.notNull(redisTemplate, "redisTemplate is null");
        Assert.notNull(stringRedisTemplate, "stringRedisTemplate is null");
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public RedisTemplate<String, String> getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    /**
     * set if not exists
     *
     * @param key      缓存Key
     * @param value    缓存Value
     * @param timeout  缓存Key过期时间值
     * @param timeUnit 缓存Key过期时间单位
     * @return
     */
    public boolean setnx(String key, Object value, long timeout, TimeUnit timeUnit) {
        Boolean result;
        if (value instanceof String) {
            result = getStringRedisTemplate().opsForValue().setIfAbsent(key, (String) value, timeout, timeUnit);
        } else {
            result = getRedisTemplate().opsForValue().setIfAbsent(key, value, timeout, timeUnit);
        }
        return result != null && result;
    }

    /**
     * 尝试获取锁，并至多等待timeout时长
     *
     * @param key     缓存Key
     * @param value   缓存value
     * @param expire  缓存Key过期时间
     * @param timeout 加锁等待超时时间
     * @param unit    时间单位
     * @return
     */
    public boolean lock(String key, String value, long expire, long timeout, TimeUnit unit) {
        long waitMillis = unit.toMillis(timeout);
        long waitAlready = 0;
        while (!setnx(key, value, expire, unit) && waitAlready < waitMillis) {
            try {
                Thread.sleep(LOCK_WAIT_THREAD_SLEEP_MILLS);
            } catch (InterruptedException ignored) {
            }
            waitAlready += LOCK_WAIT_THREAD_SLEEP_MILLS;
        }
        if (waitAlready < waitMillis) {
            return true;
        }
        logger.warn("### Redis lock failed after waiting for {}ms, key=[{}].", waitAlready, key);
        return false;
    }

    /**
     * 释放锁
     *
     * @param key   缓存Key
     * @param value 加锁时的缓存值
     * @return
     */
    public boolean releaseLock(String key, String value) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        Long returnValue = getStringRedisTemplate().execute(redisScript, Collections.singletonList(key), value);
        return RELEASE_LOCK_SUCCESS_RESULT.equals(returnValue);
    }
}