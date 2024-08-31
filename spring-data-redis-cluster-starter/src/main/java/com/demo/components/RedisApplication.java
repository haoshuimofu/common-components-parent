package com.demo.components;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;

/**
 * @author wude
 * @date 2020/7/17 14:14
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
//@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(RedisApplication.class, args);



//        RedisProperties redisProperties = (RedisProperties) ctx.getBean("customRedisProperties");
//        System.out.println("===> custom redis <===");
//        System.out.println(JSON.toJSONString(redisProperties, true));
//
//        System.out.println("----------------------");
//
//        System.out.println("===> redis properties <===");
//        for (String s : ctx.getBeanNamesForType(RedisProperties.class)) {
//            System.out.println(s);
//        }
//        RedisProperties defaultRedisProperties = (RedisProperties) ctx.getBean("spring.redis-org.springframework.boot.autoconfigure.data.redis.RedisProperties");
//        System.out.println(JSON.toJSONString(defaultRedisProperties, true));
    }

}