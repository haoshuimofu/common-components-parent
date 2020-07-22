package com.demo.components.redis.cluster.controller;

import com.demo.components.redis.cluster.CacheManager;
import com.demo.components.redis.cluster.JsonResult;
import com.demo.components.redis.cluster.RedisDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author wude
 * @date 2020/7/17 14:21
 */
@RestController
public class RedisTestController {

    private static final Logger logger = LoggerFactory.getLogger(RedisTestController.class);

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @RequestMapping("lock")
    public JsonResult lockTest() {

        String key = "redis_lock";
        String value = "the lock";
        boolean success = cacheManager.lock(key, value, 15);
        if (success) {
            logger.info("redis 加锁: [{}]=[{}], ttl=[{}]s", key, value, 15);
        }

//        logger.info("错误的value解锁: [{}]", cacheManager.unLock(key, "test"));
        logger.info("错误的value解锁: [{}]", redisDistributedLock.releaseLock(key, "test"));
        success = cacheManager.lock(key, value, 15, 3, TimeUnit.SECONDS);
        logger.info("redis 再次加锁: [{}]=[{}], success=[{}]", key, value, success);

//        logger.info("正确的value解锁: [{}]", cacheManager.unLock(key, value));
        logger.info("正确的value解锁: [{}]", redisDistributedLock.releaseLock(key, value));
        success = cacheManager.lock(key, value, 15, 3, TimeUnit.SECONDS);
        logger.info("redis 再次加锁: [{}]=[{}], success=[{}]", key, value, success);

        return JsonResult.success();

    }


}