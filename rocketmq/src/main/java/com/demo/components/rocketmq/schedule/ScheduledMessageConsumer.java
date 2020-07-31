package com.demo.components.rocketmq.schedule;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wude
 * @date 2020/7/31 15:05
 */
public class ScheduledMessageConsumer {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // Instantiate message consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ScheduledMessageConsumerGroup");
        consumer.setNamesrvAddr("localhost:9876");
        // Subscribe topics
        consumer.subscribe("ScheduledMessageTopic", "*");
        // Register message listener
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt message : messages) {
                    // Print approximate delay time period
                    System.out.println("Receive message[msgId=" + message.getMsgId() + "] "
                            + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");

                    System.err.println("queueId = " + message.getQueueId() + ", queueOffset = " + message.getQueueOffset() + ", body = [" +
                            new String(message.getBody()) + "], thread = " +Thread.currentThread().getName() + ", now = " + sdf.format(new Date()));



                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // Launch consumer
        consumer.start();
    }

}