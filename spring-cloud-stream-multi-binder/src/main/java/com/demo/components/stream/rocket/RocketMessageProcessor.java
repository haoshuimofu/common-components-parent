package com.demo.components.stream.rocket;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author wude
 * @date 2021/4/6 16:32
 */
public interface RocketMessageProcessor {

    String INPUT_SYNC_PRODUCT_INFO_TO_STATION = "inputSyncProductInfo2Station";

    String OUTPUT_SYNC_PRODUCT_INFO_TO_STATION = "outputSyncProductInfo2Station";

    @Input(INPUT_SYNC_PRODUCT_INFO_TO_STATION)
    SubscribableChannel inputSyncProductInfo2Station();

    @Output(OUTPUT_SYNC_PRODUCT_INFO_TO_STATION)
    MessageChannel outputSyncProductInfo2Station();

    // demo

    String INPUT_ROCKET_DEMO = "inputSyncProductInfo2Station";

    String OUTPUT_ROCKET_DEMO = "outputSyncProductInfo2Station";

    @Input(INPUT_ROCKET_DEMO)
    SubscribableChannel inputRocketDemo();

    @Output(OUTPUT_ROCKET_DEMO)
    MessageChannel outputRocketDemo();

}