package com.demo.components.stream;

import com.demo.components.stream.rabbit.message.DemoMessage1;
import com.demo.components.stream.rabbit.message.DemoMessage2;
import com.demo.components.stream.rabbit.DemoMessageProcessor1;
import com.demo.components.stream.rabbit.DemoMessageProcessor2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/9/4 11:25
 */
@RestController
@RequestMapping("/multi/message/send")
public class RabbitMessageController {
    @Autowired
    private DemoMessageProcessor1 demoMessageProcessor1;
    @Autowired
    private DemoMessageProcessor2 demoMessageProcessor2;

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