package com.demo.components.stream.rabbit.message.delayed;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 延迟消息
 *
 * @Author wude
 * @Create 2019-05-30 17:44
 */
public interface DelayedDemoMessageProcessor {


    String INPUT_DELAYED_DEMO = "inputDelayedDemo";
    String OUTPUT_DELAYED_DEMO = "outputDelayedDemo";

    @Input(INPUT_DELAYED_DEMO)
    SubscribableChannel inputDelayedDemo();

    @Output(OUTPUT_DELAYED_DEMO)
    MessageChannel outputDelayedDemo();
}