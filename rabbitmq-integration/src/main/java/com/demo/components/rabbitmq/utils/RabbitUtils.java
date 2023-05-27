package com.demo.components.rabbitmq.utils;

public class RabbitUtils {

    private static final String X_DEAD_LETTER_EXCHANGE_SUFFIX = ".DLX";
    private static final String X_DEAD_LETTER_QUEUE_SUFFIX = ".DLQ";

    public static String appendDLXSuffix(String exchange) {
        return exchange + X_DEAD_LETTER_EXCHANGE_SUFFIX;
    }

    public static String appendDLQSuffix(String queue) {
        return queue + X_DEAD_LETTER_QUEUE_SUFFIX;
    }

}