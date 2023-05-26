package com.demo.components.rabbitmq.test.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.components.rabbitmq.annotation.ConsumerService;
import com.demo.components.rabbitmq.test.messagebody.TopicMessageABody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ddmc
 * @Create 2019-05-29 13:48
 */
@ConsumerService
public class AbstractConsumerService extends AbstractConsumerService<TopicMessageABody> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConsumerService.class);

    @Override
    public void handleMessage(TopicMessageABody messageBody) {
        logger.info("Topic消息A: {}", JSON.toJSONString(messageBody));
//        System.out.println(1 / 0);
    }

}