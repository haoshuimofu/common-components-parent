package com.demo.components.rocketmq.message;

/**
 * 须保留无参构造器, 否则会message convert error
 * see {@link org.apache.rocketmq.spring.support.RocketMQMessageConverter}
 * 实际消费会经过 byte -> string -> jackson -> fastjson 注意尝试converter
 *
 * @author wude
 * @date 2021/4/15 17:16
 */
public class DemoMessage {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}