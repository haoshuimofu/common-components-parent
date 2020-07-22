package com.demo.components.rabbitmq.exception;

/**
 * 消息体发送之前对Body进行Check，不通过异常提示
 *
 * @Author wude
 * @Create 2019-05-31 13:27
 */
public class CheckMessageBodyException extends RuntimeException {

    public CheckMessageBodyException(String message) {
        super(message);
    }
}