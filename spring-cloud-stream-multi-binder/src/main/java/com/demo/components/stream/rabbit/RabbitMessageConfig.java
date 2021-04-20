package com.demo.components.stream.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author wude
 * @date 2021/4/20 20:04
 */
@Configuration
@EnableBinding({DemoMessageProcessor1.class, DemoMessageProcessor2.class})
public class RabbitMessageConfig {
}