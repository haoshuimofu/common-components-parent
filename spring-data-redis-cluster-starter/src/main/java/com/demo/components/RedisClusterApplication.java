package com.demo.components;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;

/**
 * @author wude
 * @date 2020/7/17 14:14
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class RedisClusterApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RedisClusterApplication.class, args);

        RedisProperties redisProperties = (RedisProperties) ctx.getBean("customRedisProperties");
        System.err.println(JSON.toJSONString(redisProperties, true));

        for (String s : ctx.getBeanNamesForType(RedisProperties.class)) {
            System.err.println(s);
        }
        RedisProperties defaultRedisProperties = (RedisProperties) ctx.getBean("spring.redis-org.springframework.boot.autoconfigure.data.redis.RedisProperties");
        System.err.println(JSON.toJSONString(defaultRedisProperties, true));
    }

    // 127.0.0.1:6000,127.0.0.1:7000,127.0.0.1:8000,27.0.0.1:6001,27.0.0.1:6002,27.0.0.1:6003,27.0.0.1:7001,27.0.0.1:7002,27.0.0.1:7003,27.0.0.1:8001,27.0.0.1:8002,27.0.0.1:8003


}