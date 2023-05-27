package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.demo.components.rabbitmq.annotation.ConsumerService;
import com.demo.components.rabbitmq.constants.QueueArgumentsKey;
import com.demo.components.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractConsumerService<T> implements ChannelAwareMessageListener, BeanNameAware, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConsumerService.class);

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private SimpleRabbitListenerContainerFactory listenerContainerFactory;

    private String beanName;
    private Class<T> messageClass;
    private SimpleMessageListenerContainer listenerContainer;

    /**
     * 消息消费业务方法
     *
     * @param messageBody
     */
    public abstract void handleMessage(T messageBody);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody()));
        T t = JSON.parseObject(message.getBody(), messageClass);
        // todo do something
        handleMessage(t);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    @Override
     public void onMessage(Message message) {
        throw new IllegalStateException("Should never be called for a ChannelAwareMessageListener");
    }

    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {
        throw new UnsupportedOperationException("This listener does not support message batches");
    }

    @Override
    public void onMessageBatch(List<Message> messages) {
        throw new UnsupportedOperationException("This listener does not support message batches");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        messageClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        consumerStarting();
    }

    private void consumerStarting() {
        ConsumerService consumerService = this.getClass().getAnnotation(ConsumerService.class);
        if (consumerService != null) {
            TopicExchange exchange = new TopicExchange(consumerService.exchange(), true, false);
            Queue queue = new Queue(consumerService.queue(), true, false, false, null);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(consumerService.routingKey());
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);

            //死信队列
            String dlxExchangeName = RabbitUtils.appendDLXSuffix(exchange.getName());
            String dlqQueueName = RabbitUtils.appendDLQSuffix(queue.getName());
            DirectExchange dlxExchange = new DirectExchange(dlxExchangeName, true, false);
            Map<String, Object> arguments = new HashMap<>();
            arguments.put(QueueArgumentsKey.X_DEAD_LETTER_EXCHANGE.getKey(), exchange.getName());
            arguments.put(QueueArgumentsKey.X_DEAD_LETTER_ROUTING_KEY.getKey(), queue.getName());
            Queue dlqQueue = new Queue(dlqQueueName, true, false, false, arguments);
            binding = BindingBuilder.bind(dlqQueue).to(dlxExchange).with(queue.getName());
            rabbitAdmin.declareExchange(dlxExchange);
            rabbitAdmin.declareQueue(dlqQueue);
            rabbitAdmin.declareBinding(binding);

            listenerContainer = listenerContainerFactory.createListenerContainer();
            listenerContainer.setMessageListener(this);
            listenerContainer.setConcurrency("2");
            listenerContainer.setQueueNames(queue.getName());
            listenerContainer.start();
            logger.info("[Rabbit] ConsumerBean启动成功! beanName={}", beanName);
        }
    }

    @Override
    public void destroy() {
        if (listenerContainer != null && listenerContainer.isActive()) {
            try {
                listenerContainer.stop();
                logger.info("[Rabbit] ConsumerBean停止成功! beanName={}", beanName);
            } catch (Exception e) {
                logger.error("[Rabbit] ConsumerBean停止错误! beanName={}", beanName);
            }
        }
    }

}

