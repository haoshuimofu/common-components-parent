package com.demo.components.rabbitmq;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @Author wude
 * @Create 2019-06-04 10:25
 */
public class CorrelationDataExtends extends CorrelationData {

    private String exchange;
    private String routingKey;
    private int retryCount;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}