package com.demo.components.rabbitmq.retry;

import com.demo.components.rabbitmq.constants.MessageHeadersKey;
import com.demo.components.rabbitmq.constants.QueueArgumentsKey;
import com.demo.components.rabbitmq.utils.RabbitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * 消息消费失败次数达到maxAttempts后消息处理
 *
 * @Author wude
 * @Create 2019-06-10 11:47
 */
public class RepublishMessageRecoverer implements MessageRecoverer {

    private static final Logger logger = LoggerFactory.getLogger(RepublishMessageRecoverer.class);

    private RabbitTemplate rabbitTemplate;

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void recover(Message message, Throwable cause) {
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        String receivedExchange = messageProperties.getReceivedExchange();
        String receivedRoutingKey = messageProperties.getReceivedRoutingKey();
        String consumerQueue = messageProperties.getConsumerQueue();
        headers.put(MessageHeadersKey.X_ORIGINAL_EXCHANGE.getKey(), receivedExchange);
        headers.put(MessageHeadersKey.X_ORIGINAL_ROUTINGKEY.getKey(), receivedRoutingKey);
        headers.put(MessageHeadersKey.X_EXCEPTION_MESSAGE.getKey(), cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage());
        headers.put(MessageHeadersKey.X_EXCEPTION_STACKTRACE.getKey(), getStackTraceAsString(cause));
        String dlxExchange = RabbitUtils.appendDLXSuffix(receivedExchange);
        this.rabbitTemplate.send(dlxExchange, consumerQueue, message);
        logger.error("###消息消费失败, 转储至死信队列: consumerQueue=[{}], receivedExchange=[{}], receivedRoutingKey=[{}], {}=[{}], {}=[{}].",
                messageProperties.getMessageId(), consumerQueue, receivedExchange, receivedRoutingKey,
                QueueArgumentsKey.X_DEAD_LETTER_EXCHANGE.getKey(), dlxExchange,
                QueueArgumentsKey.X_DEAD_LETTER_ROUTING_KEY.getKey(), consumerQueue, cause);
    }

    private String getStackTraceAsString(Throwable cause) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        cause.printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }
}