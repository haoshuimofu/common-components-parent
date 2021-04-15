package com.demo.components.stream.multibinders.rockemq;

import com.alibaba.fastjson.JSON;
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
public class RocketConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketConsumer.class);


    @StreamListener(RocketProcessor.INPUT_SYNC_PRODUCT_INFO_TO_STATION)
    public void syncProductInfo2Station(@Payload SyncProductInfo2StationMessage message) {
        long interval = System.currentTimeMillis() / 1000 - message.getTriggerTime();
        System.err.println("消费时差: " + interval + "; message=" + JSON.toJSONString(message, true));
        System.err.println();
        LOGGER.info("### syncProductInfo2Station Ok！");
    }

}