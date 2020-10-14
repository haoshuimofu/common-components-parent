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

        RedisProperties redisProperties = ctx.getBean(RedisProperties.class);
        System.err.println(JSON.toJSONString(redisProperties, true));
    }

}