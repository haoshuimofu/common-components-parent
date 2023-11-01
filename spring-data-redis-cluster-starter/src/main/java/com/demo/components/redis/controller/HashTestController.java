package com.demo.components.redis.controller;

import com.alibaba.fastjson.JSON;
import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Collections;

/**
 * @author dewu.de
 * @date 2023-11-01 3:03 下午
 */
@RestController
@RequestMapping("hash")
public class HashTestController {


    private static final Logger logger = LoggerFactory.getLogger(SetTestController.class);

    @Autowired
    private CacheManager cacheManager;


    @RequestMapping("test")
    public JsonResult<Boolean> hash() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String cacheKey = year + "_" + (month + 1) + "_hash";
        // 判断hash key是否存在
        System.out.println(cacheKey + " exists: " + cacheManager.getStringRedisTemplate().hasKey(cacheKey));
        // 直接打印hash value
        System.out.println(cacheKey + " value: " + JSON.toJSONString(cacheManager.getStringRedisTemplate().opsForHash().entries(cacheKey)));

        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(cacheKey + " 有 " + maximum + " 天");
        for (int i = 1; i <= maximum; i++) {
            /**
             * key存在，才会向hash添加field-value，并且返回结果1；反之key不存在则直接返回0
             * if redis.call("EXISTS", KEYS[1]) == 1 then
             *     redis.call("HSET", KEYS[1], ARGV[1], ARGV[2])
             *     return "1"
             * else
             *     return "0"
             * end
             */
            String script = "if redis.call(\"EXISTS\", KEYS[1]) == 1 then redis.call(\"HSET\", KEYS[1], ARGV[1], ARGV[2]) return \"1\" else return \"0\" end";
            DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            String result = cacheManager.getStringRedisTemplate().execute(redisScript, Collections.singletonList(cacheKey), String.valueOf(i), "day_" + i);
            System.out.println("field=" + String.valueOf(i) + " add result: " + result);
            if ("0".equals(result)) {
                cacheManager.getStringRedisTemplate().opsForHash().put(cacheKey, String.valueOf(i), "day_" + i);
            }
        }
        System.out.println(cacheKey + " value: " + JSON.toJSONString(cacheManager.getStringRedisTemplate().opsForHash().entries(cacheKey)));
        cacheManager.getStringRedisTemplate().delete(cacheKey);
        return JsonResult.success(true);
    }

}
