package com.demo.components.rocketmq.controller;

import com.demo.components.rocketmq.AnotherRocketConfig;
import com.demo.components.rocketmq.AnotherRocketMQTemplate;
import com.demo.components.rocketmq.RocketConfig;
import com.demo.components.rocketmq.message.AnotherUserMessage;
import com.demo.components.rocketmq.message.DemoMessage;
import com.demo.components.rocketmq.message.UserMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author wude
 * @date 2021/4/15 15:53
 */
@RestController
@RequestMapping("message")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RocketConfig rocketConfig;
    @Autowired
    private AnotherRocketMQTemplate anotherRocketMQTemplate;
    @Autowired
    private AnotherRocketConfig anotherRocketConfig;

    @RequestMapping("send/demo")
    public Object sendDemo() {
        DemoMessage message = new DemoMessage();
        message.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        rocketMQTemplate.send(rocketConfig.demoTopic,
                MessageBuilder.withPayload(message)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .build());
        return "send demo message ok";
    }

    @RequestMapping("send/user")
    public Object sendUser() {
        rocketMQTemplate.send(rocketConfig.userTopic,
                MessageBuilder.withPayload(new UserMessage().setUserName("wude").setUserAge(Byte.valueOf("18")))
                        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .build());
        return "send user message ok";
    }

    @RequestMapping("send/another/user")
    public Object sendAnotherUser() {
        AnotherUserMessage message = new AnotherUserMessage();
        message.setName("another_" + UUID.randomUUID().toString());
        anotherRocketMQTemplate.send(anotherRocketConfig.userTopic,
                MessageBuilder.withPayload(message)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .build());
        return "send another user message ok";
    }
}