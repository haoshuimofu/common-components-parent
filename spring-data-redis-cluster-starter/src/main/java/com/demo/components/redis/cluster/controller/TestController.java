package com.demo.components.redis.cluster.controller;

import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
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

    @RequestMapping("setget")
    public Object test() {
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
}