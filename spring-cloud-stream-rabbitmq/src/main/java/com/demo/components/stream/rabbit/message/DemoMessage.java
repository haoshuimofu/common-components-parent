package com.demo.components.stream.rabbit.message;

/**
 * @author ddmc
 * @date 2019/9/25 14:30
 */
public class DemoMessage {

    private String content;
    private String sender;

    public DemoMessage() {
    }

    public DemoMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}