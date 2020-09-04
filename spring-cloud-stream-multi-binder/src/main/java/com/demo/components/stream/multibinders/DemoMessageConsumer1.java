package com.demo.components.stream.multibinders;

import com.alibaba.fastjson.JSON;
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

    @StreamListener(DemoMessageProcessor1.INPUT)
    public void handleDemoMessage(DemoMessage1 message) {
        System.out.println("demoInput Received: " + JSON.toJSONString(message));
    }


}