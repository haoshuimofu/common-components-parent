package com.demo.components.rabbitmq.constants;

/**
 * 消息Headers的Key值常量设置
 *
 * @Author wude
 * @Create 2019-07-30 16:01
 */
public enum MessageHeadersKey {

    /**
     * 原交换机
     */
    X_ORIGINAL_EXCHANGE("x-original-exchange"),
    /**
     * 原routingKey
     */
    X_ORIGINAL_ROUTINGKEY("x-original-routingKey"),
    /**
     * 异常信息
     */
    X_EXCEPTION_MESSAGE("x-exception-message"),
    /**
     * 异常栈信息
     */
    X_EXCEPTION_STACKTRACE("x-exception-stacktrace");

    private String key;

    MessageHeadersKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}