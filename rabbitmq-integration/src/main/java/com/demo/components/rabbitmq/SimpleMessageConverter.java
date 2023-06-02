package com.demo.components.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class SimpleMessageConverter implements MessageConverter {

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return MessageBuilder.withBody(JSON.toJSONBytes(object)).andProperties(messageProperties).build();
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        throw new UnsupportedOperationException("This converter does not support converting message to java object.");
    }

}