package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.demo.components.rabbitmq.bind.BinderCollectors;
import com.demo.components.rabbitmq.bind.Binders;
import com.demo.components.rabbitmq.bind.BindingInfo;
import com.demo.components.rabbitmq.utils.MessageBodyUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseConsumerService<T extends BaseMessageBody> implements ChannelAwareMessageListener, BeanNameAware, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(BaseConsumerService.class);

    private static final Map<String, Exchange> DECLARED_EXCHANGES = new ConcurrentHashMap<>();
    private static final String HANDLE_MESSAGE_METHOD = "handleMessage"; // 消息处理方法

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private SimpleRabbitListenerContainerFactory listenerContainerFactory;
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private BinderCollectors binderCollectors;

    private String beanName;
    private Class<? extends BaseMessageBody> actualMessageBodyClass; // 实际消息体类
    private SimpleMessageListenerContainer listenerContainer;        // 消息监听器容器实例

    @Value("${spring.rabbitmq.redeclare:false}")
    private boolean redeclare;

    /**
     * 消息消费业务方法
     *
     * @param messageBody
     */
    public abstract void handleMessage(T messageBody);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        T t = JSON.parseObject(message.getBody(), actualMessageBodyClass);
        // todo do something
        handleMessage(t);
//        channel.basicAck(;);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        actualMessageBodyClass = (Class<? extends BaseMessageBody>)
                ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        start();
    }

    private void start() {
        // 根据消息体实际类型解析queue exchange routing 和 binding信息, 然后declare后启动消息监听器
        BindingInfo bindingInfo = MessageBodyUtils.parseBindingInfo(actualMessageBodyClass);
        Binders binders = MessageBodyUtils.generateBinders(bindingInfo);
        // 保存queue和messageBodyClass映射关系，便于后续消息反序列化成java对象
        ((FastJsonMessageConverter) messageConverter).mappedQueueMessageBodyClass(bindingInfo.getQueue(), actualMessageBodyClass);
        Exchange exchange = DECLARED_EXCHANGES.get(binders.getExchange().getName());
        if (exchange == null) {
            rabbitAdmin.declareExchange(binders.getExchange());
            DECLARED_EXCHANGES.put(binders.getExchange().getName(), binders.getExchange());
        }
        Exchange dlxExchange = DECLARED_EXCHANGES.get(binders.getDlxExchange().getName());
        if (dlxExchange == null) {
            rabbitAdmin.declareExchange(binders.getDlxExchange());
            DECLARED_EXCHANGES.put(binders.getDlxExchange().getName(), binders.getDlxExchange());
        }
        if (redeclare) {
            rabbitAdmin.deleteQueue(binders.getQueue().getName());
        }
        rabbitAdmin.declareQueue(binders.getQueue());
        rabbitAdmin.declareBinding(binders.getBinding());

        if (redeclare) {
            rabbitAdmin.deleteQueue(binders.getDlqQueue().getName());
        }
        rabbitAdmin.declareQueue(binders.getDlqQueue());
        rabbitAdmin.declareBinding(BindingBuilder.bind(binders.getDlqQueue())
                .to(binders.getDlxExchange())
                .with(binders.getQueue().getName()));
        binderCollectors.putDeclaredBinders(actualMessageBodyClass, binders);

        // 为当前消费者启动一个消息监听器
//        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(this);
//        listenerAdapter.setDefaultListenerMethod(HANDLE_MESSAGE_METHOD);
//        listenerAdapter.setMessageConverter(messageConverter); // 一定要设置，否则消费参数类型是byte[]
        listenerContainer = listenerContainerFactory.createListenerContainer();
        listenerContainer.setMessageListener(this);
        listenerContainer.setConcurrency("10");
        listenerContainer.setQueueNames(binders.getQueue().getName());
        listenerContainer.start();
        logger.info("### MQ-消息监听器启动...bean=[{}], queue=[{}], {}Exchange=[{}], routingKey=[{}], dlqQueue=[{}], dlxExchange=[{}], dlxRoutingKey=[{}].",
                beanName,
                binders.getQueue().getName(),
                binders.getExchange().getType(), binders.getExchange().getName(),
                binders.getBinding().getRoutingKey(),
                binders.getDlqQueue().getName(),
                binders.getDlxExchange().getName(),
                binders.getDlxBinding().getRoutingKey());
    }

    @Override
    public void destroy() {
        if (listenerContainer != null && listenerContainer.isActive()) {
            logger.info("### MQ-消息监听器随着消费者Bean的销毁而停止...{}", beanName);
            listenerContainer.stop();
        }
    }

}

