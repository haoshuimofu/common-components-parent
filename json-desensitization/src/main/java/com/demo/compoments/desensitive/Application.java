package com.demo.compoments.desensitive;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * @Author wude
 * @Create 2019-05-09 15:16
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {

    }

}