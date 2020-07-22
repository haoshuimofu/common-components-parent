package com.demo.components.rabbitmq.constants;

import org.springframework.amqp.core.Queue;

/**
 * Queue.arguments的Key值常量定义, 参考{@link Queue#getArguments()}
 *
 * @Author wude
 * @Create 2019-06-17 14:02
 */
public enum QueueArgumentsKey {

    X_DEAD_LETTER_EXCHANGE("x-dead-letter-exchange"),
    X_DEAD_LETTER_ROUTING_KEY("x-dead-letter-routing-key");

    private String key;

    QueueArgumentsKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}