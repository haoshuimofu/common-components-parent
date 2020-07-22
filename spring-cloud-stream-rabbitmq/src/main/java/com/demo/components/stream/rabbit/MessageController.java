package com.demo.components.stream.rabbit;

import com.demo.components.stream.rabbit.message.DemoMessage;
import com.demo.components.stream.rabbit.message.DemoMessageConsumer;
import com.demo.components.stream.rabbit.message.DemoMessageProcessor;
import com.demo.components.stream.rabbit.message.delayed.DelayedDemoMessageBody;
import com.demo.components.stream.rabbit.message.delayed.DelayedDemoMessageProcessor;
import com.demo.components.stream.rabbit.message.pollable.SyncProduct2StationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

/**
 * 消息发送控制器
 *
 * @Author wude
 * @Create 2019-05-30 17:16
 */
@RestController
@RequestMapping("/message/send")
public class MessageController {

    @Autowired
    private DemoMessageProcessor demoMessageProcessor;
    @Autowired
    private DemoMessageConsumer demoMessageConsumer;
    @Autowired
    private DelayedDemoMessageProcessor delayedDemoMessageProcessor;

    @RequestMapping("demo")
    @ResponseBody
    public String send() {
        Message<DemoMessage> message = MessageBuilder.withPayload(new DemoMessage("spring-cloud-stream", "wude")).build();
        demoMessageProcessor.outputDemo().send(message);
        return "send demo message success";
//        return demoMessageConsumer.handle(new DemoMessage("spring-cloud-stream", "wude"));
    }


    @GetMapping("delayedDemo")
    public Object sendDelayedDemoMessage() {
        DelayedDemoMessageBody messageBody = new DelayedDemoMessageBody();
        messageBody.setContent("这是一条测试消息!");
        messageBody.setSendTime(new Date());
        delayedDemoMessageProcessor.outputDelayedDemo()
                .send(MessageBuilder.withPayload(messageBody).setHeader("x-delay", 5000).build()); //构建消息并且发送;
        return messageBody;
    }

    @GetMapping("pollableMessage")
    public Object pollableMessage() {
        SyncProduct2StationMessage message = new SyncProduct2StationMessage();
        message.setProductIds(Arrays.asList("1", "2", "3"));
        message.setTriggerTime(new Date());
        return message;
    }

}