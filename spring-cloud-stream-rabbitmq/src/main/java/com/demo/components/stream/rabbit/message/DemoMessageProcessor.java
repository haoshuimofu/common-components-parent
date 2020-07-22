package com.demo.components.stream.rabbit.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * The @Input annotation identifies an input channel, through which received messages enter the application.
 * The @Output annotation identifies an output channel, through which published messages leave the application.
 * The @Input and @Output annotations can take a channel name as a parameter. If a name is not provided, the name of the annotated method is used.
 *
 * @author ddmc
 * @date 2019/9/25 14:24
 */
public interface DemoMessageProcessor {

    /**
     * Input channel name.
     */
    String INPUT = "inputDemo";
    String INPUT_1 = "inputDemo1";
    /**
     * 轮询模式消费
     * Starting with version 2.0, you can now bind a pollable consumer
     */
    /*@Input(DemoMessageProcessor.INPUT)
    PollableMessageSource inputDemo();*/

    String OUTPUT = "outputDemo";

    /**
     * @return input channel.
     */
    @Input(DemoMessageProcessor.INPUT)
    SubscribableChannel inputDemo();

    @Input(DemoMessageProcessor.INPUT_1)
    SubscribableChannel inputDemo1();

    @Output(DemoMessageProcessor.OUTPUT)
    MessageChannel outputDemo();

}