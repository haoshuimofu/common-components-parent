package com.demo.components.stream.rabbit.message;

/**
 * @author ddmc
 * @date 2019/9/25 14:30
 */
public class DemoMessage1 {

    private String content;
    private String sender;

    public DemoMessage1() {
    }

    public DemoMessage1(String content, String sender) {
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