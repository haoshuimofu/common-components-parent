package com.demo.components.stream.rabbit.message.delayed;

import java.util.Date;

/**
 * 延迟消息体
 *
 * @Author wude
 * @Create 2019-05-30 17:46
 */
public class DelayedDemoMessageBody {

    private String content;
    private Date sendTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}