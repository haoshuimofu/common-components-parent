package com.demo.components.rabbitmq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 消费者Bean声明注解
 *
 * @Author wude
 * @Create 2019-05-21 11:01
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ConsumerService {

    String exchange();

    String routingKey();

    String queue();

}