package com.demo.components.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.components.rocketmq.message.UserMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author wude
 * @date 2021/4/15 18:02
 */
@Service
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}",
        topic = "${rocketmq.topic.userTopic}",
        consumerGroup = "user_consumer")
public class UserMessageConsumer implements RocketMQListener<UserMessage> {

    @Override
    public void onMessage(UserMessage message) {
        System.err.println(JSON.toJSONString(message, true));
    }

}