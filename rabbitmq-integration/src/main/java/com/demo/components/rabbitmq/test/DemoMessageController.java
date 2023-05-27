package com.demo.components.rabbitmq.test;

import com.alibaba.fastjson.JSON;
import com.demo.components.rabbitmq.WrappedRabbitTemplate;
import com.demo.components.rabbitmq.test.message.DemoMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/message")
public class DemoMessageController {

    @Autowired
    private WrappedRabbitTemplate wrappedRabbitTemplate;

    @RequestMapping("send")
    public Object send() {
        DemoMessage message = new DemoMessage();
        Message msg = new Message(JSON.toJSONBytes(message), new MessageProperties());
        wrappedRabbitTemplate.getRabbitTemplate().send("demo_exchange", "demo", msg);
        return message;
    }

}
