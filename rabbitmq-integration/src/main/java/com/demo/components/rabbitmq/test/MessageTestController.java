package com.demo.components.rabbitmq.test;

import com.demo.components.rabbitmq.RabbitmqProducer;
import com.demo.components.rabbitmq.test.messagebody.TopicMessageABody;
import com.demo.components.rabbitmq.test.messagebody.TopicMessageBBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @Author ddmc
 * @Create 2019-05-22 9:54
 */
@RestController
@RequestMapping("message")
public class MessageTestController {

    private final RabbitmqProducer rabbitmqProducer;

    public MessageTestController(RabbitmqProducer rabbitmqProducer) {
        this.rabbitmqProducer = rabbitmqProducer;
    }

    @RequestMapping("send")
    public Object send() throws UnsupportedEncodingException {
        TopicMessageABody aBody = new TopicMessageABody();
//        rabbitmqProducer.convertAndSend(aBody); // 需要设置MessageConverter
        rabbitmqProducer.send(aBody);
        return aBody;
    }

    @RequestMapping("send1")
    public Object send1() {
        TopicMessageBBody bBody = new TopicMessageBBody();
        StringBuffer sb = new StringBuffer();
        int len = 100;
        for (int i = 0; i < len; i++) {
            sb.append(UUID.randomUUID().toString());
        }
        rabbitmqProducer.send(bBody);
        return bBody;
    }

}