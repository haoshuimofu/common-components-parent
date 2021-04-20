package com.demo.components.stream;

import com.alibaba.fastjson.support.spring.messaging.MappingFastJsonMessageConverter;
import com.demo.components.stream.rocket.RocketMessageProcessor;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {RocketMQAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @StreamMessageConverter
    public MappingFastJsonMessageConverter messageConverter() {
        // Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        MappingFastJsonMessageConverter messageConverter = new MappingFastJsonMessageConverter();
        return messageConverter;
    }

}