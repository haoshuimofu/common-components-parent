package com.demo.components.stream.rabbit.message.delayed;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 延迟消息消费端
 *
 * @Author wude
 * @Create 2019-05-30 17:45
 */
@EnableBinding({DelayedDemoMessageProcessor.class})
public class DelayedDemoMessageConsumer {

    Logger log = LoggerFactory.getLogger(DelayedDemoMessageConsumer.class);

    @StreamListener(DelayedDemoMessageProcessor.INPUT_DELAYED_DEMO)
    public void handleDelayedDemoMessage(DelayedDemoMessageBody messageBody) throws Exception {
        log.info("延迟时间: {}", System.currentTimeMillis() - messageBody.getSendTime().getTime());
        log.info("延迟消息测试: " + JSON.toJSONString(messageBody));
    }

}