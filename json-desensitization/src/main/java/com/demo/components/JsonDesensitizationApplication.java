package com.demo.components;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * @Author wude
 * @Create 2019-05-09 15:16
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class JsonDesensitizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonDesensitizationApplication.class, args);
    }

}