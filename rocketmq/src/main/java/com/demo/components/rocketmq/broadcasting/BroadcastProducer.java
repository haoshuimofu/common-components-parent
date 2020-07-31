package com.demo.components.rocketmq.broadcasting;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wude
 * @date 2020/7/31 14:43
 */
public class BroadcastProducer {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        DefaultMQProducer producer = new DefaultMQProducer("BroadcastProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 100; i++){
            Message msg = new Message("BroadcastTopic",
                    "TagA",
                    "OrderID188",
                    ("Hello world at " + sdf.format(new Date())).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

}