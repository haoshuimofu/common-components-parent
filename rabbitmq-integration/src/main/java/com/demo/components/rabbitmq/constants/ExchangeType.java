package com.demo.components.rabbitmq.constants;

/**
 * 交换机类型
 *
 * @Author wude
 * @Create 2019-06-10 16:55
 */
public enum ExchangeType {

    /**
     * Direct exchange.
     */
    DIRECT("direct"),

    /**
     * Topic exchange.
     */
    TOPIC("topic"),

    /**
     * Fanout exchange.
     */
    FANOUT("fanout"),

    /**
     * Headers exchange.
     */
    HEADERS("headers"),

    /**
     * System exchange.
     */
    SYSTEM("system");

    private String type;

    ExchangeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}