package com.demo.components.rabbitmq.bind;

import com.demo.components.rabbitmq.constants.ExchangeType;

/**
 * 消息队列绑定信息
 *
 * @Author wude
 * @Create 2019-06-10 17:36
 */
public class BindingInfo {

    private String queue;
    private String exchangeName;
    private ExchangeType exchangeType;
    private String routingKey;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}