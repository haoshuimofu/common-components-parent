package com.demo.components.rabbitmq.annotation;

import com.demo.components.rabbitmq.constants.ExchangeType;

/**
 * 交换机声明注解
 *
 * @Author wude
 * @Create 2019-06-10 16:51
 */
public @interface Exchange {

    /**
     * 类型
     *
     * @return
     */
    ExchangeType type();

    /**
     * 名称
     *
     * @return
     */
    String name();

}
