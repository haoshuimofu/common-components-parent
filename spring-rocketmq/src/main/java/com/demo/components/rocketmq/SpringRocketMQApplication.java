package com.demo.components.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author wude
 * @date 2021/4/15 15:52
 */
@SpringBootApplication
public class SpringRocketMQApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(SpringRocketMQApplication.class, args);

    }
}