package com.demo.components.rabbitmq.utils;

import com.demo.components.rabbitmq.BaseMessageBody;
import com.demo.components.rabbitmq.annotation.Binding;
import com.demo.components.rabbitmq.bind.Binders;
import com.demo.components.rabbitmq.bind.BindingInfo;
import com.demo.components.rabbitmq.constants.ExchangeType;
import com.demo.components.rabbitmq.constants.QueueArgumentsKey;
import com.demo.components.rabbitmq.exception.IllegalMessageBodyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息体类工具方法
 *
 * @Author wude
 * @Create 2019-06-10 17:27
 */
public class MessageBodyUtils {

    private static final boolean durable = true;
    private static final boolean exclusive = false;
    private static final boolean autoDelete = false;

    /**
     * 解析消息体类绑定信息
     *
     * @param messageBody
     * @return
     */
    public static BindingInfo parseBindingInfo(Class<? extends BaseMessageBody> messageBody) {
        Binding binding = messageBody.getAnnotation(Binding.class);
        if (binding == null) {
            throw new IllegalMessageBodyException("消息体类没有标注注解@" + Binding.class.getSimpleName() + "! " + messageBody.getTypeName());
        }
        // 非Fanout类型交换机时和Queue绑定需要routingKey
        if (binding.exchange().type() != ExchangeType.FANOUT && StringUtils.isBlank(binding.routingKey())) {
            throw new IllegalMessageBodyException("消息体类注解@" + Binding.class.getSimpleName() + "信息缺少routingKey! " + messageBody.getTypeName());
        }
        BindingInfo bindingInfo = new BindingInfo();
        bindingInfo.setQueue(binding.queue().name());
        bindingInfo.setExchangeName(binding.exchange().name());
        bindingInfo.setExchangeType(binding.exchange().type());
        bindingInfo.setRoutingKey(binding.routingKey());
        return bindingInfo;
    }

    /**
     * 根据消息绑定信息生成绑定对象组合实例用以Declare
     *
     * @param bindingInfo
     * @return
     */
    public static Binders generateBinders(BindingInfo bindingInfo) {
        Queue queue = new Queue(bindingInfo.getQueue(), durable, exclusive, autoDelete, buildQueueArguments(bindingInfo));
        Exchange exchange = null;
        org.springframework.amqp.core.Binding binding = null;
        switch (bindingInfo.getExchangeType()) {
            case DIRECT:
                exchange = new DirectExchange(bindingInfo.getExchangeName(), durable, autoDelete);
                binding = BindingBuilder.bind(queue).to(exchange).with(bindingInfo.getRoutingKey()).noargs();
                break;
            case TOPIC:
                exchange = new TopicExchange(bindingInfo.getExchangeName(), durable, autoDelete);
                binding = BindingBuilder.bind(queue).to(exchange).with(bindingInfo.getRoutingKey()).noargs();
                break;
            case FANOUT:
                exchange = new FanoutExchange(bindingInfo.getExchangeName(), durable, autoDelete);
                binding = BindingBuilder.bind(queue).to((FanoutExchange) exchange);
                break;
            default:
                break;
        }
        if (exchange == null) {
            throw new IllegalMessageBodyException("消息体类的交换机类型暂不支持! " + bindingInfo.getExchangeType().getType());
        }
        Binders binders = new Binders(exchange, queue, binding);
        // 死信队列和死信交换机
        binders.setDlqQueue(new Queue(RabbitUtils.appendDLQSuffix(bindingInfo.getQueue()), durable, exclusive, autoDelete));
        binders.setDlxExchange(new DirectExchange((String) queue.getArguments().get(QueueArgumentsKey.X_DEAD_LETTER_EXCHANGE.getKey())));
        binders.setDlxBinding(BindingBuilder.bind(binders.getDlqQueue())
                .to(binders.getDlxExchange())
                .with((String) queue.getArguments().get(QueueArgumentsKey.X_DEAD_LETTER_ROUTING_KEY.getKey())));
        return binders;
    }

    /**
     * 创建Queue时构建arguments用以保存死信交换机和死信routingKey
     *
     * @param bindingInfo 绑定信息
     * @return
     */
    private static Map<String, Object> buildQueueArguments(BindingInfo bindingInfo) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(QueueArgumentsKey.X_DEAD_LETTER_EXCHANGE.getKey(), RabbitUtils.appendDLXSuffix(bindingInfo.getExchangeName()));
        arguments.put(QueueArgumentsKey.X_DEAD_LETTER_ROUTING_KEY.getKey(), bindingInfo.getQueue());
        return arguments;
    }

}