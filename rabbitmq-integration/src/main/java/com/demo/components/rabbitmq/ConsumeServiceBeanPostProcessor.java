package com.demo.components.rabbitmq;

import com.demo.components.rabbitmq.annotation.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者业务Bean校验器, {@link org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor}
 *
 * @author wude
 * @date 2019/9/21 16:03
 */
@Component
public class ConsumeServiceBeanPostProcessor implements BeanPostProcessor, SmartInitializingSingleton {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeServiceBeanPostProcessor.class);

    private final AtomicInteger mqConsumerServiceBeanCount = new AtomicInteger(0);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = AopUtils.getTargetClass(bean);
        ConsumerService consumerService = beanClass.getAnnotation(ConsumerService.class);
        if (consumerService != null) {
            Assert.isTrue(bean instanceof BaseConsumerService, "MQ-消费者Bean必须要继承自基类(" + BaseConsumerService.class.getTypeName() + ")");
            mqConsumerServiceBeanCount.incrementAndGet();
        }
        return bean;
    }

    @Override
    public void afterSingletonsInstantiated() {
        logger.info("### MQ-共有<<<{}>>>个消费者初始化且其消息监听器启动成功!", mqConsumerServiceBeanCount.get());
    }
}