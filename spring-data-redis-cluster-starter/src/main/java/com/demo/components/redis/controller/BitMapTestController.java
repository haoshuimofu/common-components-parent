package com.demo.components.redis.controller;

import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * @author dewu.de
 * @date 2023-03-23 5:20 下午
 */
@RestController
@RequestMapping("bitmap/test")
public class BitMapTestController {

    private static final Logger logger = LoggerFactory.getLogger(BitMapTestController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("sign")
    public JsonResult<Boolean> lock() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String cacheKey = year + "_" + month;

        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(cacheKey + " 有 " + maximum + " 天");
        for (int i = 0; i < maximum; i++) {
            boolean state = i % 3 == 0;
            boolean result = cacheManager.getStringRedisTemplate().opsForValue().setBit(cacheKey, i, state);
            System.out.print(i + "=" + state + " ");
        }
        System.out.println();
        for (int i = 0; i < maximum; i++) {
            boolean result = cacheManager.getStringRedisTemplate().opsForValue().getBit(cacheKey, i);
            System.out.print(i + "=" + result + " ");
        }
        System.out.println();
        long days = cacheManager.bitCount(cacheKey);
        System.out.println(cacheKey + " 签到了 " + days + " 天");
        int mid = maximum / 2;
        cacheManager.getStringRedisTemplate().opsForValue().setBit(cacheKey, mid, true);
        for (int i = 0; i < maximum; i++) {
            boolean result = cacheManager.getStringRedisTemplate().opsForValue().getBit(cacheKey, i);
            System.out.print(i + "=" + result + " ");
        }
        System.out.println();
        days = cacheManager.bitCount(cacheKey);
        System.out.println(cacheKey + " 签到了 " + days + " 天");


        return JsonResult.success(true);
    }


}
