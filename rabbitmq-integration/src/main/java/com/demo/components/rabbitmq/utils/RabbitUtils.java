package com.demo.components.rabbitmq.utils;

/**
 * 工具方法
 *
 * @Author wude
 * @Create 2019-07-30 16:40
 */
public class RabbitUtils {

    /**
     * 死信交换机名称后缀
     */
    private static final String X_DEAD_LETTER_EXCHANGE_SUFFIX = ".DLX";
    /**
     * 死信队列名称后缀
     */
    private static final String X_DEAD_LETTER_QUEUE_SUFFIX = ".DLQ";

    /**
     * 追加死信交换机名称后缀
     *
     * @param exchange
     * @return
     */
    public static String appendDLXSuffix(String exchange) {
        return exchange + X_DEAD_LETTER_EXCHANGE_SUFFIX;
    }

    /**
     * 追加死信队列名称后缀
     *
     * @param queue
     * @return
     */
    public static String appendDLQSuffix(String queue) {
        return queue + X_DEAD_LETTER_QUEUE_SUFFIX;
    }

}