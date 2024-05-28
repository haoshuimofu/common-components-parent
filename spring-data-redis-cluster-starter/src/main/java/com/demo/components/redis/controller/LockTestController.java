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

        boolean success = cacheManager.tryLock(lockKey, lockValue, 20);
        logger.info("### 第1次加锁: lock_key=[{}], lock_value=[{}], success=[{}]", lockKey, lockValue, success);

        success = cacheManager.tryLock(lockKey, lockValue, 200);
        logger.info("### 第2次加锁: lock_key=[{}], lock_value=[{}], success=[{}]", lockKey, lockValue, success);

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        success = cacheManager.tryLock(lockKey, lockValue, 20);
        logger.info("### 第3次加锁: lock_key=[{}], lock_value=[{}], success=[{}]", lockKey, lockValue, success);


        String releaseLockValue = lockValue + "-1";
        success = cacheManager.releaseLock(lockKey, releaseLockValue);
        logger.info("### 释放锁: key=[{}], value=[{}], success=[{}]", lockKey, releaseLockValue, success);

        releaseLockValue = lockValue + "-2";
        success = cacheManager.releaseLock(lockKey, releaseLockValue);
        logger.info("### 再次释放锁: key=[{}], value=[{}], success=[{}]", lockKey, releaseLockValue, success);

        success = cacheManager.releaseLock(lockKey, lockValue);
        logger.info("### 正确释放锁: key=[{}], value=[{}], success=[{}]", lockKey, lockValue, success);


        return JsonResult.success(true);
    }

}