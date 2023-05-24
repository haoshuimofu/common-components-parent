package com.demo.components.rabbitmq.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 消费者类Bean初始化以后进行MQ消费者的初始化
 *
 * @Author wude
 * @Create 2019-05-21 11:11
 */
@Component
public class ConsumerStartupListener implements InitializingBean, ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerStartupListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.error("### ApplicationListener.onApplicationEvent");
        ApplicationContext applicationContext = event.getApplicationContext();
        // root application context
        if (applicationContext.getParent() == null) {
            // todo doSomething
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.error("### ApplicationContextAware.setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.error("### InitializingBean.afterPropertiesSet");
    }
}