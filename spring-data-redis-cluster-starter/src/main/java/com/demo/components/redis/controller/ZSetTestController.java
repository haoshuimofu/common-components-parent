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
@RequestMapping("zset/test")
public class ZSetTestController {

    private static final Logger logger = LoggerFactory.getLogger(ZSetTestController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("score")
    public JsonResult<Boolean> zset() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String cacheKey = year + "_" + (month + 1) + "_zset";

        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(cacheKey + " 有 " + maximum + " 天");
        for (int i = 0; i < maximum; i++) {
            String day = String.valueOf(i + 1);
            double score = (int) Math.round(new Random().nextInt(1000));
            score = (i + 1) * 10;
            boolean result = cacheManager.getStringRedisTemplate().opsForZSet().add(cacheKey, day, score);
            System.out.print(day + "=" + score + " ");
        }
        System.out.println();


        Set<String> resList = cacheManager.getStringRedisTemplate().opsForZSet().range(cacheKey, 0, 2);
        System.out.println(JSON.toJSONString(resList));

        resList = cacheManager.getStringRedisTemplate().opsForZSet().reverseRange(cacheKey, 0, 2);
        System.out.println(JSON.toJSONString(resList));

        return JsonResult.success(true);
    }


}
