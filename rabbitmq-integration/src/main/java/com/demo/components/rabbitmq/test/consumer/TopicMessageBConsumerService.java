package com.demo.components.rabbitmq.test.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.components.rabbitmq.BaseConsumerService;
import com.demo.components.rabbitmq.test.messagebody.TopicMessageBBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ddmc
 * @Create 2019-05-29 13:48
 */
//@ConsumerService
public class TopicMessageBConsumerService extends BaseConsumerService<TopicMessageBBody> {

    private static final Logger logger = LoggerFactory.getLogger(TopicMessageBConsumerService.class);

    @Override
    public void handleMessage(TopicMessageBBody messageBody) {
        logger.info("Topic消息B: {}", JSON.toJSONString(messageBody));
    }

}