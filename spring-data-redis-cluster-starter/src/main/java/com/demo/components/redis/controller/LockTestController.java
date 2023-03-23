package com.demo.components.redis.controller;

import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/10/14 16:35
 */
@RestController
@RequestMapping("lock/test")
public class LockTestController {

    private static final Logger logger = LoggerFactory.getLogger(LockTestController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("lock")
    public JsonResult<Boolean> lock(@RequestParam("lockKey") String lockKey, @RequestParam("lockValue") String lockValue) {

        boolean result = cacheManager.tryLock(lockKey, lockValue, 60);
        logger.info("### 加锁: lock_key=[{}], lock_value=[{}], result=[{}]", lockKey, lockValue, result);

//        try {
//            Thread.sleep(10_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        result = cacheManager.releaseLock(lockKey, lockValue + "-1");
        logger.info("### 释放锁: key=[{}], value=[{}], result=[{}]", lockKey, lockValue, result);

        result = cacheManager.releaseLock(lockKey, lockValue + "-2");
        logger.info("### 再次释放锁: key=[{}], value=[{}], result=[{}]", lockKey, lockValue, result);

        return JsonResult.success(result);
    }

}