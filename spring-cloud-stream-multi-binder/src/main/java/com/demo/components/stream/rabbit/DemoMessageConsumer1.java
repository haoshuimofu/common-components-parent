package com.demo.components.stream.rabbit;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Demo 消息消费者
 *
 * @author ddmc
 * @date 2019/9/25 14:26
 */
@EnableBinding({DemoMessageProcessor1.class})
public class DemoMessageConsumer1 {

    private static final Logger logger = LoggerFactory.getLogger(DemoMessageConsumer1.class);

    @StreamListener(DemoMessageProcessor1.INPUT)
    public void handleDemoMessage(DemoMessage1 message) {
        logger.error("### demoInput Received: " + JSON.toJSONString(message));
    }


}