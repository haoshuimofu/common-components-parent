package com.demo.components.rabbitmq.test.message;

import com.demo.components.rabbitmq.BaseMessageBody;
import com.demo.components.rabbitmq.annotation.Binding;
import com.demo.components.rabbitmq.annotation.Exchange;
import com.demo.components.rabbitmq.annotation.Queue;
import com.demo.components.rabbitmq.constants.ExchangeType;

/**
 * Topic消息A
 * PS: *匹配一个单词，#匹配零或者多个单词，一定要.隔开才算
 * 比如: topic.*.a能接收top.log.a的消息，但top.log.write.a的接收不了，能被top.#.a的接受
 *
 * @Author ddmc
 * @Create 2019-05-29 15:43
 */
@Binding(queue = @Queue(name = "topic_queue_a"),
        exchange = @Exchange(type = ExchangeType.TOPIC, name = "ddmc_topic"),
        routingKey = "topic.#.a")
public class TopicMessageABody extends BaseMessageBody {
    @Override
    public void preCheck() {

    }
}