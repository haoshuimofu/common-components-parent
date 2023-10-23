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
import java.util.Set;

/**
 * @author dewu.de
 * @date 2023-10-23 7:18 下午
 */
@RestController
@RequestMapping("set")
public class SetTestController {

    private static final Logger logger = LoggerFactory.getLogger(SetTestController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("test")
    public JsonResult<Boolean> set() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String cacheKey = year + "_" + (month + 1) + "_set";

        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(cacheKey + " 有 " + maximum + " 天");
        int total = 0;
        for (int i = 0; i < maximum; i++) {
            String day = String.valueOf(i + 1);
            long addCnt = cacheManager.getStringRedisTemplate().opsForSet().add(cacheKey, day);
            total += addCnt;
        }
        System.out.println("value total=" + total);
        Set<String> members = cacheManager.getStringRedisTemplate().opsForSet().members(cacheKey);
        System.out.println(JSON.toJSONString(members));

        long cnt = cacheManager.getStringRedisTemplate().opsForSet().add(cacheKey, "10");
        System.out.println("add 10 to set, result=" + cnt);

        cnt = cacheManager.getStringRedisTemplate().opsForSet().remove(cacheKey, "10");
        System.out.println("remove 10 from set, result=" + cnt);
        members = cacheManager.getStringRedisTemplate().opsForSet().members(cacheKey);
        System.out.println(JSON.toJSONString(members));

        return JsonResult.success(true);
    }

}