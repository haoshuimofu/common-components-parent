package com.demo.components.rocketmq.broadcasting;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author wude
 * @date 2020/7/31 14:43
 */
public class BroadcastConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("BroadcastConsumerGroup");
        consumer.setNamesrvAddr("localhost:9876");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //set to broadcast mode
        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.subscribe("BroadcastTopic", "TagA || TagC || TagD");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");

                for (MessageExt msg : msgs) {
                    if (msg.getQueueId() == 0 || msg.getQueueId() == 1)
                    System.err.println("queueId = " + msg.getQueueId() + ", queueOffset = " + msg.getQueueOffset() + ", body = [" +
                            new String(msg.getBody()) + "], thread = " +Thread.currentThread().getName());
                }


                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        });

        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");
    }

}