package com.demo.components.rabbitmq.exception;

/**
 * 消息体类非法异常
 *
 * @Author wude
 * @Create 2019-07-31 10:48
 */
public class IllegalMessageBodyException extends RuntimeException {

    public IllegalMessageBodyException(String message) {
        super(message);
    }
}