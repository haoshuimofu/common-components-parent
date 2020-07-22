package com.demo.components.rabbitmq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息队列 和 交换机 绑定关系声明注解
 *
 * @Author wude
 * @Create 2019-06-10 17:17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Binding {

    /**
     * 消息队列
     *
     * @return
     */
    Queue queue();

    /**
     * 交换机
     *
     * @return
     */
    Exchange exchange();

    /**
     * 路由键, FanoutExchange时无需此项
     *
     * @return
     */
    String routingKey() default "";
}