package com.demo.components.redis.controller;

import com.alibaba.fastjson.JSON;
import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Random;
import java.util.Set;

/**
 * @author dewu.de
 * @date 2023-03-23 5:20 下午
 */
@RestController
@RequestMapping("list/test")
public class ListTestController {

    private static final Logger logger = LoggerFactory.getLogger(ListTestController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("list")
    public JsonResult<Boolean> zset() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String cacheKey = year + "_" + (month + 1) + "_list";
        System.out.println(cacheManager.getStringRedisTemplate().opsForList().size(cacheKey));
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < maximum; i++) {
            String day = String.valueOf(i + 1);
            cacheManager.getStringRedisTemplate().opsForList().leftPush(cacheKey, day);
        }
        System.out.println();

        System.out.println(cacheManager.getStringRedisTemplate().opsForList().leftPop(cacheKey));
        System.out.println(cacheManager.getStringRedisTemplate().opsForList().rightPop(cacheKey));
        return JsonResult.success(true);
    }


}
