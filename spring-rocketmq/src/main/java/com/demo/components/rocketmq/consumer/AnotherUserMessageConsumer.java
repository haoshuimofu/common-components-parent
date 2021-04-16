package com.demo.components.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.components.rocketmq.message.AnotherUserMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author wude
 * @date 2021/4/16 11:04
 */
@Service
@RocketMQMessageListener(nameServer = "${another.rocketmq.name-server}",
        topic = "${another.rocketmq.topic.userTopic}",
        consumerGroup = "another_user_consumer")
public class AnotherUserMessageConsumer implements RocketMQListener<AnotherUserMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnotherUserMessageConsumer.class);

    @Override
    public void onMessage(AnotherUserMessage message) {
        LOGGER.info("### anohter rocketmq, message={}", JSON.toJSONString(message, true));

    }

}