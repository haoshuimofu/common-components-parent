package com.demo.components.stream.rocket;

import com.alibaba.fastjson.JSON;
import com.demo.components.stream.rocket.message.RocketDemoMessage;
import com.demo.components.stream.rocket.message.SyncProductInfo2StationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2021/4/6 16:34
 */
@Component
public class RocketMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMessageConsumer.class);


    @StreamListener(RocketMessageProcessor.INPUT_SYNC_PRODUCT_INFO_TO_STATION)
    public void syncProductInfo2Station(@Payload SyncProductInfo2StationMessage message) {
        long interval = System.currentTimeMillis() / 1000 -
                (message.getTriggerTime() != null ? message.getTriggerTime() : 0);
        System.err.println("消费时差: " + interval + "; message=" + JSON.toJSONString(message, true));
        LOGGER.info("### syncProductInfo2Station Ok！");
    }

    @StreamListener(RocketMessageProcessor.INPUT_SYNC_PRODUCT_INFO_TO_STATION)
    public void rocketDemoMessage(@Payload RocketDemoMessage message) {
        System.err.println(JSON.toJSONString(message, true));
    }

}