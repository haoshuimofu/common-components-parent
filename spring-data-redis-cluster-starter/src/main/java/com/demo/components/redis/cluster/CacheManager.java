package com.demo.components.redis.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
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
    private final static long lock_thread_sleep_time = 100;
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final Long RELEASE_LOCK_SUCCESS_RESULT = 1L;

    private final RedisTemplate redisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;

    public CacheManager(RedisTemplate redisTemplate, RedisTemplate<String, String> stringRedisTemplate) {
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
     * 缓存String
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存String, 带超时时间, 时间单位为秒
     *
     * @param key
     * @param value
     * @param expiration
     */
    public void setString(String key, String value, int expiration) {
        stringRedisTemplate.opsForValue().set(key, value, expiration, TimeUnit.SECONDS);
    }

    /**
     * 获取String value
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存value
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存value
     *
     * @param key
     * @param value
     * @param expiration
     */
    public void set(String key, Object value, int expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据缓存Key清除缓存
     *
     * @param key
     * @return
     */
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Object gets(String key) {
        return redisTemplate.opsForList().range(key, 0, redisTemplate.opsForList().size(key) - 1);
    }

    /**
     * setnx: 超时单位默认秒
     *
     * @param key     缓存Key
     * @param value   缓存Value
     * @param timeout 缓存ttl
     * @return
     */
    public boolean setnx(String key, Object value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    public Long putList(String key, Object... values) {
        if (values == null || values.length == 0) {
            return 0L;
        }
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 尝试获取锁（立即返回）
     *
     * @param key    锁的redis key
     * @param value  锁的value
     * @param expire 过期时间/秒
     * @return 是否获取成功
     */
    public boolean lock(String key, String value, long expire) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁，并至多等待timeout时长
     *
     * @param key     锁的redis key
     * @param value   锁的value
     * @param expire  过期时间/秒
     * @param timeout 超时时长
     * @param unit    时间单位
     * @return 是否获取成功
     */
    public boolean lock(String key, String value, long expire, long timeout, TimeUnit unit) {
        long waitMillis = unit.toMillis(timeout);
        long waitAlready = 0;

        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS) && waitAlready < waitMillis) {
            try {
                Thread.sleep(lock_thread_sleep_time);
            } catch (InterruptedException e) {
                logger.error("Interrupted when trying to get a lock. key: {}", key, e);
            }
            waitAlready += lock_thread_sleep_time;
        }

        if (waitAlready < waitMillis) {
            return true;
        }
        logger.warn("<====== lock {} failed after waiting for {} ms", key, waitAlready);
        return false;
    }

    /**
     * 释放锁
     *
     * @param key   锁的redis key
     * @param value 锁的value
     */
    public boolean unLock(String key, String value) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        long result = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), value);
        return Objects.equals(result, RELEASE_LOCK_SUCCESS_RESULT);
    }
}