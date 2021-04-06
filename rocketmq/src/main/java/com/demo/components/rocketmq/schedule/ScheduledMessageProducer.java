package com.demo.components.rocketmq.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 18 DelayTimeLevel
 * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 *
 * @author wude
 * @date 2020/7/31 15:06
 */
public class ScheduledMessageProducer {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ScheduledMessageProducerGroup");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(300000);
        // Launch producer
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("ScheduledMessageTopic", ("Hello scheduled message " + i + " at " + sdf.format(new Date())).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(4);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

}