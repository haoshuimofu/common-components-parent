package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于alibaba.fastjson自定义消息转换器
 *
 * @Author wude
 * @Create 2019-07-31 18:11
 */
public class FastJsonMessageConverter extends AbstractMessageConverter {

    /**
     * 消息队列和消息体类映射关系
     */
    private static final Map<String, Class<? extends BaseMessageBody>> QUEUE_MESSAGEBODY_MAPPINGS = new ConcurrentHashMap<>();
    private static final String ERROR_MESSAGE_FOR_UNKNOWN_MESSAGE_BODY_CLASS =
            "Convert message to object failed, because of unknown message body class for queue[%s]";

    public void mappedQueueMessageBodyClass(String queue, Class<? extends BaseMessageBody> messageBodyClass) {
        Assert.notNull(queue, "queue is null!");
        Assert.notNull(messageBodyClass, "messageBodyClass is null!");
        QUEUE_MESSAGEBODY_MAPPINGS.put(queue, messageBodyClass);
    }

    private void clear() {
        QUEUE_MESSAGEBODY_MAPPINGS.clear();
    }

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        byte[] bytes = JSON.toJSONBytes(object);
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(IOUtils.UTF8.name());
        messageProperties.setContentLength(bytes.length);
        return new Message(bytes, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        Class<? extends BaseMessageBody> messageBodyClass = QUEUE_MESSAGEBODY_MAPPINGS.get(consumerQueue);
        Assert.notNull(messageBodyClass, String.format(ERROR_MESSAGE_FOR_UNKNOWN_MESSAGE_BODY_CLASS, consumerQueue));
        return JSON.parseObject(message.getBody(), messageBodyClass);
    }
}