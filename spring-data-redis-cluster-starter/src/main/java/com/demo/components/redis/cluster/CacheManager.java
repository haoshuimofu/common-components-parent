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
    /**
     * 锁释放lua script
     */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public CacheManager(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        Assert.notNull(redisTemplate, "redisTemplate is null");
        Assert.notNull(stringRedisTemplate, "stringRedisTemplate is null");
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    /**
     * set if not exists
     *
     * @param key      缓存Key
     * @param value    缓存Value
     * @param timeout  缓存Key过期时间值
     * @param timeUnit 缓存Key过期时间单位
     * @return set cache 成功与否
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
     * @return 加锁是否成功
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
        logger.warn("### Redis lock failed after waiting {}ms, key=[{}].", waitAlready, key);
        return false;
    }

    /**
     * 释放锁
     *
     * @param key   缓存Key
     * @param value 加锁时的缓存值
     * @return 释放锁是否成功
     */
    public boolean releaseLock(String key, String value) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        Long returnValue = getStringRedisTemplate().execute(redisScript, Collections.singletonList(key), value);
        return Long.valueOf(1).equals(returnValue);
    }
}