package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WrappedRabbitTemplate implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(WrappedRabbitTemplate.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            logger.error("### Rabbit 消息发送至Exchange失败: ack={}, cause={}, correlationData={}.", false, cause, JSON.toJSONString(correlationData));
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
        } else {
            logger.info("### Rabbit 消息发送至Exchange成功!: ack={}, cause={}, correlationData={}.", false, cause, JSON.toJSONString(correlationData));
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("Rabbit returnCallback: exchange={}, routingKey={}, replyCode={}, replyText={}",
                exchange, routingKey, replyCode, replyText);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(this);
        //强制消息投递至queue
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }
}
