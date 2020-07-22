package com.demo.components.rabbitmq;

import java.io.Serializable;

/**
 * 消息体基类
 *
 * @Author wude
 * @Create 2019-05-21 10:00
 */
public abstract class BaseMessageBody implements Serializable {

    /**
     * 发送人
     */
    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * 消息发送之前校验
     */
    public abstract void preCheck();

}