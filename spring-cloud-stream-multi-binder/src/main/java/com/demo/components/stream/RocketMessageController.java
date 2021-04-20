package com.demo.components.stream;

import com.demo.components.stream.rocket.RocketMessageProcessor;
import com.demo.components.stream.rocket.message.RocketDemoMessage;
import com.demo.components.stream.rocket.message.SyncProductInfo2StationMessage;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

/**
 * @author wude
 * @date 2021/4/20 14:39
 */
@RestController
@RequestMapping("message/rocket")
public class RocketMessageController {

    @Autowired
    private RocketMessageProcessor rocketMessageProcessor;

    @RequestMapping("send")
    @ResponseBody
    public String rocketSend() {
        SyncProductInfo2StationMessage message = new SyncProductInfo2StationMessage();
        message.setProductIds(Arrays.asList("1", "a"));
        message.setTriggerTime(System.currentTimeMillis() / 1000);

        Message msg = MessageBuilder.withPayload(message)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 4)
                .setHeader("__STARTDELIVERTIME", new Date().getTime() + 30000).build();
        rocketMessageProcessor.outputSyncProductInfo2Station().send(msg);

        RocketDemoMessage demoMessage = new RocketDemoMessage();
        msg = MessageBuilder.withPayload(demoMessage).build();
        rocketMessageProcessor.outputRocketDemo().send(msg);

        return "send rocket message success";
    }

}