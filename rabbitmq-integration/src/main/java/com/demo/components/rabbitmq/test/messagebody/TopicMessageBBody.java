package com.demo.components.rabbitmq.test.messagebody;

import com.demo.components.rabbitmq.BaseMessageBody;
import com.demo.components.rabbitmq.annotation.Binding;
import com.demo.components.rabbitmq.annotation.Exchange;
import com.demo.components.rabbitmq.annotation.Queue;
import com.demo.components.rabbitmq.constants.ExchangeType;

/**
 * Topic消息B
 *
 * @Author ddmc
 * @Create 2019-05-29 15:43
 */
@Binding(queue = @Queue(name = "topic_queue_b"),
        exchange = @Exchange(type = ExchangeType.TOPIC, name = "ddmc_topic"),
        routingKey = "topic.#.b")
public class TopicMessageBBody extends BaseMessageBody {
    @Override
    public void preCheck() {

    }
}