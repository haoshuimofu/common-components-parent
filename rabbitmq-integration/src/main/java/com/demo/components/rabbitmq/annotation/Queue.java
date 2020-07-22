package com.demo.components.rabbitmq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息队列声明注解
 *
 * @Author wude
 * @Create 2019-06-10 16:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Queue {

    /**
     * 队列名
     *
     * @return
     */
    String name();

}