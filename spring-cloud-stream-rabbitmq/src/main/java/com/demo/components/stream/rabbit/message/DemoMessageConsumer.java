package com.demo.components.stream.rabbit.message;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Demo 消息消费者
 *
 * @author ddmc
 * @date 2019/9/25 14:26
 */
@EnableBinding({DemoMessageProcessor.class})
public class DemoMessageConsumer {

    /**
     * {@link org.springframework.cloud.stream.annotation.StreamListener}
     * 如果只有StreamListener注解，则不能有返回值，否则报错：A method annotated with @StreamListener having a return type should also have an outbound target specified
     * {@link org.springframework.messaging.handler.annotation.SendTo}
     * 方法返回对象output发送至某个channel，需要返回值
     *
     * @param message
     * @return
     */
    @StreamListener(DemoMessageProcessor.INPUT)
//    @SendTo(DemoMessageProcessor.OUTPUT)
    public void handleDemoMessage(DemoMessage message) {
        System.out.println("demoInput Received: " + JSON.toJSONString(message));
//        System.out.println(1 / 0);
//        return "success";
    }


    @StreamListener(DemoMessageProcessor.INPUT_1)
    public void handle1(DemoMessage message) {
        System.out.println("demoInput1 Received: " + JSON.toJSONString(message));
//        System.out.println(1 / 0);
    }

}