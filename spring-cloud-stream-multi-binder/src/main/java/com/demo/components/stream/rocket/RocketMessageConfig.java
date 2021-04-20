package com.demo.components.stream.rocket;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author wude
 * @date 2021/4/20 20:02
 */
@Configuration
@EnableBinding(RocketMessageProcessor.class)
public class RocketMessageConfig {
}