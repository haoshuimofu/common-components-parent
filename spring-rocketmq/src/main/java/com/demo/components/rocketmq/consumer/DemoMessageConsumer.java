package com.demo.components.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.components.rocketmq.message.DemoMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author wude
 * @date 2021/4/15 18:02
 */
@Service
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}",
        topic = "${rocketmq.topic.demoTopic}",
        consumerGroup = "demo_consumer")
public class DemoMessageConsumer implements RocketMQListener<DemoMessage> {

    @Override
    public void onMessage(DemoMessage message) {
        System.err.println(JSON.toJSONString(message, true));
    }

}