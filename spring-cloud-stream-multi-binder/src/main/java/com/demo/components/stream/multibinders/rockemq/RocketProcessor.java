package com.demo.components.stream.multibinders.rockemq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author wude
 * @date 2021/4/6 16:32
 */
public interface RocketProcessor {

    String INPUT_SYNC_PRODUCT_INFO_TO_STATION = "inputSyncProductInfo2Station";

    String OUTPUT_SYNC_PRODUCT_INFO_TO_STATION = "outputSyncProductInfo2Station";

    @Input(INPUT_SYNC_PRODUCT_INFO_TO_STATION)
    SubscribableChannel inputSyncProductInfo2Station();

    @Output(OUTPUT_SYNC_PRODUCT_INFO_TO_STATION)
    MessageChannel outputSyncProductInfo2Station();

}