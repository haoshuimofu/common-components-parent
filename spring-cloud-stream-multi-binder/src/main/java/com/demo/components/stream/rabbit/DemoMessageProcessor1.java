package com.demo.components.stream.rabbit;

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
public interface DemoMessageProcessor1 {

    /**
     * Input channel name.
     */
    String INPUT = "inputDemo_1";

    String OUTPUT = "outputDemo_1";

    /**
     * @return input channel.
     */
    @Input(DemoMessageProcessor1.INPUT)
    SubscribableChannel inputDemo();

    @Output(DemoMessageProcessor1.OUTPUT)
    MessageChannel outputDemo();

}