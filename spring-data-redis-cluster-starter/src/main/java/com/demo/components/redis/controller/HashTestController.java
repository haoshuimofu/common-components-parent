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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>lua脚本中KEYS在执行的时候需要保证key在一个slot上，否则无法保证原子操作</p>
 * <p>
 *     CROSSSLOT Keys in request don't hash to the same slot#012 at redis.clients.jedis.Protocol.processError(Protocol.java:127)#012
 *     at redis.clients.jedis.Protocol.process(Protocol.java:161)#012 at redis.clients.jedis.Protocol.read(Protocol.java:215)#012
 *     at redis.clients.jedis.Connection.readProtocolWithCheckingBroken(Connection.java:340)#012 at
 * </p>
 * <p>当hash最后一个hash key被删除时, cache key也就被删除了</p>
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
            String script = "if redis.call(\"EXISTS\", KEYS[1]) == 1 then redis.call(\"HSET\", KEYS[1], ARGV[1], ARGV[2]) redis.call(\"EXPIRE\", KEYS[1], ARGV[3]) return \"1\" else return \"0\" end";
            DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            String result = cacheManager.getStringRedisTemplate().execute(redisScript, Collections.singletonList(cacheKey), String.valueOf(i), "day_" + i, "3600");
            System.out.println("field=" + i + " add result: " + result);
            if ("0".equals(result)) {
                cacheManager.getStringRedisTemplate().opsForHash().put(cacheKey, String.valueOf(i), "day_" + i);
            }
        }
        List<String> hashKvs = new ArrayList<>(4);
        hashKvs.add("time");
        hashKvs.add(String.valueOf(System.currentTimeMillis()));
        hashKvs.add("data");
        hashKvs.add("123");
//        hashKvs.add(String.valueOf(600));

        String tempScript = "if redis.call(\"EXISTS\", KEYS[1]) == 1 then redis.call('HMSET', KEYS[1], unpack(ARGV)) redis.call('expire', KEYS[1], 3600) return \"1\" end";
        DefaultRedisScript<String> s = new DefaultRedisScript<>(tempScript, String.class);
        Object tempResult = cacheManager.getStringRedisTemplate().execute(s, Collections.singletonList(cacheKey), hashKvs.toArray(new String[]{}));
        System.out.println("temp result=" + tempResult);

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cacheKey + "ttl: " + cacheManager.getStringRedisTemplate().getExpire(cacheKey, TimeUnit.SECONDS));

//        for (int i = 1; i <= maximum; i++) {
//            Long result = cacheManager.getStringRedisTemplate().opsForHash().delete(cacheKey, String.valueOf(i));
//            System.out.println("field=" + i + " delete result: " + result);
//        }

        System.out.println("exists=" + cacheManager.getStringRedisTemplate().hasKey(cacheKey));


        System.out.println(cacheKey + " value: " + JSON.toJSONString(cacheManager.getStringRedisTemplate().opsForHash().entries(cacheKey)));
        cacheManager.getStringRedisTemplate().delete(cacheKey);

        String script = "if redis.call(\"EXISTS\", KEYS[1]) == 1 then return redis.call(\"HGETALL\", KEYS[1]) else return nil end";
        String result = cacheManager.getStringRedisTemplate().execute(new DefaultRedisScript<>(script, String.class), Collections.singletonList(cacheKey), new String[]{});
        System.out.println("result=" + result + ", ==null" + (result == null) + ", null str=" + ("null".equals(result)));
        cacheManager.getStringRedisTemplate().delete(cacheKey);

        return JsonResult.success(true);
    }

}
