package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

public class CustomedMessageConverter extends AbstractMessageConverter {

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
        // 需要知道反序列化成哪个Java类的示例
//        return JSON.parseObject(message.getBody(), messageClass);
        throw new UnsupportedOperationException("请自行反序列化消息");
    }
}