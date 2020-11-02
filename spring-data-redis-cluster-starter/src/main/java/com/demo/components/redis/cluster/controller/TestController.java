package com.demo.components.redis.cluster.controller;

import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
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

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("setget")
    public Object test() {
        String key = "test_key";
        String uuid = UUID.randomUUID().toString();
        cacheManager.getRedisTemplate().opsForValue()
                .set(key, JsonResult.success("test_value"), 600, TimeUnit.MINUTES);
        cacheManager.getStringRedisTemplate().opsForValue().set(uuid, uuid, 30, TimeUnit.SECONDS);
        return cacheManager.getRedisTemplate().opsForValue().get(key);
    }
}