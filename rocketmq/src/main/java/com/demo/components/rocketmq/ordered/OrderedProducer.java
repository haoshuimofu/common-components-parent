package com.demo.components.rocketmq.ordered;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wude
 * @date 2020/7/30 10:17
 */
public class OrderedProducer {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        //Instantiate with a producer group name.a
        DefaultMQProducer producer = new DefaultMQProducer("OrderedProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("OrderedMessageTopic", tags[i % tags.length], "KEY" + i,
                    ("Hello RocketMQ " + i + " " + sdf.format(new Date())).getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {

                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();

                    MessageQueue queue = mqs.get(index);
                    System.err.println("id = " + id + ", index = " + index + " ,queueId = " + queue.getQueueId() + ", body = [" + new String(msg.getBody()) + "]");
                    return mqs.get(index);
                }
            }, orderId);

//            System.out.printf("%s%n", sendResult);
        }
        //server shutdown
        producer.shutdown();
    }


}