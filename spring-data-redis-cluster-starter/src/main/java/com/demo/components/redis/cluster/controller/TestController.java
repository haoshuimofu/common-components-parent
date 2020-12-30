package com.demo.components.redis.cluster.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import com.demo.components.redis.cluster.CacheObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wude
 * @date 2020/10/14 16:35
 */
@RestController
@RequestMapping("redis/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("lock")
    public Object lock() {
        String key = "test_key";
        String uuid = UUID.randomUUID().toString();
        cacheManager.getStringRedisTemplate().opsForValue().set(key, uuid, 600, TimeUnit.SECONDS);
        logger.error("### put cache is redis: {}={}", key, uuid);

        boolean lockResult = cacheManager.lock(key, uuid, 10, 3, TimeUnit.SECONDS);
        logger.error("### 加锁等待3秒钟: key=[{}], success=[{}]", key, lockResult);

        boolean releaseResult = cacheManager.releaseLock(key, key);
        logger.error("### 错误value释放锁: key=[{}], value=[{}], success=[{}]", key, key, releaseResult);

        releaseResult = cacheManager.releaseLock(key, uuid);
        logger.error("### 正确value释放锁: key=[{}], value=[{}], success=[{}]", key, uuid, releaseResult);

        return JsonResult.success(lockResult);
    }

    @RequestMapping("cache")
    public Object cache() {
        String cacheKey = "cache_key";
        CacheObject cacheObj = new CacheObject("王博", 18);
        cacheManager.getRedisTemplate().opsForValue().set(cacheKey, cacheObj, 600, TimeUnit.SECONDS);

        JSONObject obj = (JSONObject) cacheManager.getRedisTemplate().opsForValue().get(cacheKey);


        String hash_cache_key = "hash_cache_key";
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("obj", cacheObj);
        cacheManager.getRedisTemplate().opsForHash().putAll(hash_cache_key, valueMap);
        cacheManager.getRedisTemplate().opsForHash().increment(hash_cache_key, "id", 100);

        List<Object> list = cacheManager.getRedisTemplate().opsForHash().multiGet(hash_cache_key, Arrays.asList("id", "obj"));
        for (Object o : list) {
            System.err.println(o.getClass().getName() + " --->>> " + o.toString());
        }

        String string_cache_key = "string_cache_key";
        cacheManager.getStringRedisTemplate().opsForValue().set(string_cache_key, "5", 600, TimeUnit.SECONDS);
        System.err.println(cacheManager.getStringRedisTemplate().opsForValue().get(string_cache_key));
        cacheManager.getStringRedisTemplate().opsForValue().increment(string_cache_key);
        System.err.println(cacheManager.getStringRedisTemplate().opsForValue().get(string_cache_key));
        cacheManager.getStringRedisTemplate().opsForValue().increment(string_cache_key, 10);
        System.err.println(cacheManager.getStringRedisTemplate().opsForValue().get(string_cache_key));

        String stock_key = UUID.randomUUID().toString();
        System.err.println(cacheManager.getStringRedisTemplate().opsForValue().get(stock_key));
        cacheManager.getStringRedisTemplate().opsForValue().increment(stock_key);
        System.err.println(cacheManager.getStringRedisTemplate().opsForValue().get(stock_key));

        return JsonResult.success(obj);
    }
}