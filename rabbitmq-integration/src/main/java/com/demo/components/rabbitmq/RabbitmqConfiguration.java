package com.demo.components.rabbitmq;

import com.demo.components.rabbitmq.bind.BinderCollectors;
import com.demo.components.rabbitmq.retry.RepublishMessageRecoverer;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptorFactoryBean;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Rabbitmq自动装配类
 *
 * @Author wude
 * @Create 2019-05-21 16:12
 */
@Configuration
@EnableConfigurationProperties(value = {RabbitProperties.class})
public class RabbitmqConfiguration {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public BinderCollectors binderCollectors() {
        return new BinderCollectors();
    }

    @Bean
    public ConnectionNameStrategy connectionNameStrategy() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

    @Bean("rabbitConnectionFactory")
    public ConnectionFactory connectionFactory(ConnectionNameStrategy connectionNameStrategy) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
        connectionFactory.setConnectionTimeout(Math.toIntExact(rabbitProperties.getConnectionTimeout().getSeconds() * 1000));
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        // connectionFactory.setChannelCacheSize(1024); max is 2048 per connection
        connectionFactory.setConnectionNameStrategy(connectionNameStrategy);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(@Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setUserCorrelationId(true);
        // 避免Producer和Consumer因为Connection产生死锁,
        // @See https://docs.spring.io/spring-amqp/docs/2.1.6.RELEASE/reference/html/#_blocked_connections_and_resource_constraints
        rabbitTemplate.setUsePublisherConnection(true);
        // rabbitTemplate.setBeforePublishPostProcessors(new GZipPostProcessor()); // 消息发送之前进行gzip压缩
        return rabbitTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(RabbitAdmin.class)
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RepublishMessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        RepublishMessageRecoverer messageRecoverer = new RepublishMessageRecoverer();
        messageRecoverer.setRabbitTemplate(rabbitTemplate);
        return messageRecoverer;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());
        // 如果多数消息都出错，重试会一直占着channel，导致消费端性能直线下降
        // The channelMax limit is reached. Try later.
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(1)); // = initial + (n-1) retries
        // retryTemplate.setRetryPolicy(new NeverRetryPolicy());
        return retryTemplate;
    }

    @Bean
    public StatelessRetryOperationsInterceptorFactoryBean retryOperationsInterceptorFactoryBean(
            RepublishMessageRecoverer messageRecoverer, RetryTemplate retryTemplate) {
        StatelessRetryOperationsInterceptorFactoryBean interceptorFactoryBean =
                new StatelessRetryOperationsInterceptorFactoryBean();
        interceptorFactoryBean.setMessageRecoverer(messageRecoverer);
        interceptorFactoryBean.setRetryOperations(retryTemplate);
        return interceptorFactoryBean;
    }

    /**
     * 消息监听器容器工厂类，用于创建消息监听器容器类实例并给与其公共配置
     *
     * @param connectionFactory
     * @param retryOperationsInterceptorFactoryBean
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            StatelessRetryOperationsInterceptorFactoryBean retryOperationsInterceptorFactoryBean) {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 转发至死信队列时确认消息，不然原始队列会阻塞
        // 消费失败拦截
        // 正常情况下: Queue.arguments带上了x-dead-letter-exchange 和 x-dead-letter-routing-key, 消息消费失败reject时会自动进入死信队列
        // 这里我们拦截失败消费(重试次数已经达到maxAttempts), 把错误栈信息放到Header里, 然后手动发送到死信交换机
        listenerContainerFactory.setAdviceChain(retryOperationsInterceptorFactoryBean.getObject());
        // listenerContainerFactory.setAfterReceivePostProcessors(new GUnzipPostProcessor());
        return listenerContainerFactory;
    }

    @Bean
    @ConditionalOnBean(RabbitAdmin.class)
    public RabbitmqProducer rabbitmqProducer(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate, BinderCollectors binderCollectors) {
        return new RabbitmqProducer(rabbitAdmin, rabbitTemplate, binderCollectors);
    }

}