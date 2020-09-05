package com.demo.components.stream.multibinders;

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
@EnableBinding({DemoMessageProcessor2.class})
public class DemoMessageConsumer2 {

    private static final Logger logger = LoggerFactory.getLogger(DemoMessageConsumer2.class);

    @StreamListener(DemoMessageProcessor2.INPUT)
    public void handleDemoMessage(DemoMessage2 message) {
//        System.err.println(1 / 0);
        logger.error("### demoInput Received: " + JSON.toJSONString(message));
    }

}