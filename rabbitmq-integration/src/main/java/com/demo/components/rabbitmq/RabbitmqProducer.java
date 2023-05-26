package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.demo.components.rabbitmq.bind.Binders;
import com.demo.components.rabbitmq.utils.MessageBodyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;
import java.util.UUID;

/**
 * 消息发送类
 *
 * @Author wude
 * @Create 2019-05-22 9:26
 */
public class RabbitmqProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProducer.class);
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final MessageBinderMappingContainer messageBinderMappingContainer;
    private final Object lock = new Object();

    public RabbitmqProducer(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate, MessageBinderMappingContainer messageBinderMappingContainer) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.messageBinderMappingContainer = messageBinderMappingContainer;
    }

    public <T extends BaseMessageBody> void send(T messageBody) {
        messageBody.preCheck();
        Binders binders = getBinders(messageBody);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(UUID.randomUUID().toString());
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(IOUtils.UTF8.name());


        Message message = new Message(JSON.toJSONBytes(messageBody), messageProperties);
        CorrelationDataExtends correlationData = new CorrelationDataExtends();
        correlationData.setId(messageProperties.getMessageId());
        correlationData.setReturnedMessage(message);
        correlationData.setExchange(binders.getExchange().getName());
        correlationData.setRoutingKey(binders.getBinding().getRoutingKey());
        Message receivedMessage = rabbitTemplate.sendAndReceive(binders.getExchange().getName(), binders.getBinding().getRoutingKey(), message, correlationData);
        System.out.println(JSON.toJSONString(receivedMessage));
    }

    public <T extends BaseMessageBody> void convertAndSend(T messageBody) {
        messageBody.preCheck();
        Binders binders = getBinders(messageBody);
        rabbitTemplate.convertAndSend(binders.getExchange().getName(), binders.getBinding().getRoutingKey(), messageBody);
    }

    /**
     * 获取消息体类对应的绑定集对象，如果不存在则尝试declare exchange/queue/binding，以确保消息从发送到交换机 到 投递至消息队列 畅通
     *
     * @param messageBody
     * @param <T>
     * @return
     */
    private <T extends BaseMessageBody> Binders getBinders(T messageBody) {
        Class<? extends BaseMessageBody> messageBodyClass = messageBody.getClass();
        Binders binders = messageBinderMappingContainer.getDeclaredBinders(messageBodyClass);
        if (binders == null) {
            synchronized (lock) {
                binders = messageBinderMappingContainer.getDeclaredBinders(messageBodyClass);
                if (binders == null) {
                    binders = MessageBodyUtils.generateBinders(MessageBodyUtils.parseBindingInfo(messageBodyClass));
                    rabbitAdmin.declareExchange(binders.getExchange());
                    rabbitAdmin.declareQueue(binders.getQueue());
                    rabbitAdmin.declareBinding(binders.getBinding());
                    rabbitAdmin.declareExchange(binders.getDlxExchange());
                    rabbitAdmin.declareQueue(binders.getDlqQueue());
                    rabbitAdmin.declareBinding(binders.getDlxBinding());
                    messageBinderMappingContainer.putDeclaredBinders(messageBodyClass, binders);
                    logger.info("### MQ-发送时消息体类组件declare: queue=[{}], {}Exchange=[{}], routingKey=[{}], messageBodyClass=[{}].",
                            binders.getQueue().getName(), binders.getExchange().getType(),
                            binders.getExchange().getName(), binders.getBinding().getRoutingKey(), messageBody.getClass().getTypeName());
                }
            }
        }
        return binders;
    }

    /**
     * 消息发送至交换机确认回调, 可以将交换机删除后发送消息测试
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 消息投送至exchange失败回调
        if (!ack) {
            logger.error("### 消息发送至交换机失败: ack=[{}], cause=[{}], correlationData=[{}].", false, cause, JSON.toJSONString(correlationData));
            if (correlationData != null) {
                if (correlationData instanceof CorrelationDataExtends) {
                    CorrelationDataExtends correlationDataExtends = (CorrelationDataExtends) correlationData;
                    int retry = 3;
                    if (correlationDataExtends.getRetryCount() < retry) {
                        // 重试次数+1
                        correlationDataExtends.setRetryCount(correlationDataExtends.getRetryCount() + 1);
                        // 重发消息
                        rabbitTemplate.send(correlationDataExtends.getExchange(), correlationDataExtends.getRoutingKey(),
                                Objects.requireNonNull(correlationData.getReturnedMessage()), correlationDataExtends);
                        logger.error("### 消息发送至交换机失败: 重试次数({}) + 1后发送重试", ((CorrelationDataExtends) correlationData).getRetryCount() - 1);
                    } else {
                        logger.error("### 消息发送至交换机失败: 重试次数已达到({})次, 存至DB等待补发!", ((CorrelationDataExtends) correlationData).getRetryCount());
                    }
                } else {
                    logger.error("### 消息发送至交换机失败: 存至DB等待补发!");
                }
            }
        }
    }

    /**
     * 消息投递到exchange后，没有找到与之绑定的queue，这时触发回调，前提是设置rabbitTemplate.mandatory=true，否则丢弃
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("### 消息投递至队列失败返回: exchange={}, routingKey={}, replyCode={}, replyText={}, Message={}.",
                exchange, routingKey, replyCode, replyText, JSON.toJSONString(message));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        rabbitTemplate.setConfirmCallback(this);
        // mandatory 参数告诉服务器至少将该消息路由到一个队列中， 否则将消息返 回给生产者。
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }
}