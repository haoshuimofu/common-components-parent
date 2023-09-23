package com.demo.components.rabbitmq.test.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.components.rabbitmq.AbstractConsumerService;
import com.demo.components.rabbitmq.annotation.ConsumerService;
import com.demo.components.rabbitmq.test.message.DemoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;

@ConsumerService(exchange = "demo_exchange", routingKey = "demo", queue = "demo_queue")
public class DemoConsumerService extends AbstractConsumerService<DemoMessage> {

    private static final Logger logger = LoggerFactory.getLogger(DemoConsumerService.class);

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }

    @Override
    public void handleMessage(DemoMessage messageBody) {
        logger.info("Receive message: {}", JSON.toJSONString(messageBody));
        System.out.println(1 / 0);
    }

}
