package com.demo.components.stream.rabbit.message.pollable;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;

/**
 * The @Input annotation identifies an input channel, through which received messages enter the application.
 * The @Output annotation identifies an output channel, through which published messages leave the application.
 * The @Input and @Output annotations can take a channel name as a parameter. If a name is not provided, the name of the annotated method is used.
 *
 * @author ddmc
 * @date 2019/9/25 14:24
 */
public interface SyncProduct2StationProcessor {

    /**
     * Input channel name
     */
    String INPUT = "inputSyncProduct2Station";
    /**
     * Output channel name
     */
    String OUTPUT = "outputSyncProduct2Station";

    /**
     * a pollable consumer
     */
    @Input(SyncProduct2StationProcessor.INPUT)
    PollableMessageSource inputSyncProduct2Station();

    @Output(SyncProduct2StationProcessor.OUTPUT)
    MessageChannel outputSyncProduct2Station();

}