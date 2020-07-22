package com.demo.components.stream.rabbit.message.pollable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Spring Cloud Stream Reference Guide
 * https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/2.1.0.RC3/single/spring-cloud-stream.html#spring-cloud-streams-overview-using-polled-consumers
 *
 * @author wude
 * @date 2020/3/6 11:46
 */
@Configuration
public class PollableMessageConfig {

    private static final Logger logger = LoggerFactory.getLogger(PollableMessageConfig.class);
    private static final ExecutorService SYNC_PRODUCT_POOL = Executors.newFixedThreadPool(3);

    @Bean
    public ApplicationRunner runner(@Qualifier(SyncProduct2StationProcessor.INPUT) PollableMessageSource input,
                                    @Qualifier(SyncProduct2StationProcessor.OUTPUT) MessageChannel output,
                                    SyncProduct2StationConsumer syncProduct2StationConsumer) {
        return args -> {
            logger.info("### Poll模式消息消费: 同步商品信息至服务站商品索引......");
            SYNC_PRODUCT_POOL.execute(() -> {
                boolean result = false;
                while (true) {
                    result = input.poll(message -> {
                        SyncProduct2StationMessage syncMessage = (SyncProduct2StationMessage) message.getPayload();
                        try {
                            syncProduct2StationConsumer.consume(syncMessage);
                        } catch (Exception e) {
                            logger.error("### 同步商品信息至服务站商品索引出错! productId=[{}].", syncMessage.getProductIds().size(), e);
                            // StaticMessageHeaderAccessor.getAcknowledgmentCallback(message).acknowledge(AcknowledgmentCallback.Status.REJECT);
                        }
                    }, new ParameterizedTypeReference<SyncProduct2StationMessage>() {
                    });
                    if (!result) {
                        try {
                            Thread.sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        };
    }
}