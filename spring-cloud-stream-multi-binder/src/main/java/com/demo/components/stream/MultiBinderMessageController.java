package com.demo.components.stream;

import com.demo.components.stream.rabbit.DemoMessage1;
import com.demo.components.stream.rabbit.DemoMessage2;
import com.demo.components.stream.rabbit.DemoMessageProcessor1;
import com.demo.components.stream.rabbit.DemoMessageProcessor2;
import com.demo.components.stream.rocket.RocketMessageProcessor;
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
 * @date 2020/9/4 11:25
 */
@RestController
@RequestMapping("/multi/message/send")
public class MultiBinderMessageController {
    @Autowired
    private DemoMessageProcessor1 demoMessageProcessor1;
    @Autowired
    private DemoMessageProcessor2 demoMessageProcessor2;
    @Autowired
    private RocketMessageProcessor rocketProcessor;

    public static void main(String[] args) {

    }

    @RequestMapping("demo")
    @ResponseBody
    public String send() {
        Message<DemoMessage1> message = MessageBuilder.withPayload(new DemoMessage1("rabbit1", "wude")).build();
        demoMessageProcessor1.outputDemo().send(message);

        Message<DemoMessage2> message2 = MessageBuilder.withPayload(new DemoMessage2("rabbit2", "wude")).build();
        demoMessageProcessor2.outputDemo().send(message2);
        return "send demo message success";
//        return demoMessageConsumer.handle(new DemoMessage("spring-cloud-stream", "wude"));
    }


}