package com.demo.components.stream.rocket;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * <a>https://blog.csdn.net/u013078871/article/details/109046756</a>
 *<p>
 *     RocketMQComponent4BinderAutoConfiguration，RocketMQBinderAutoConfiguration，这两个autoConfig类先后初始化，第一个是RocketMQ实例初始化，第二个是Binder初始化。其中RocketMQComponent4BinderAutoConfiguration @AutoConfigureAfter(RocketMQAutoConfiguration.class)，而RocketMQBinderAutoConfiguration @Import({ RocketMQAutoConfiguration.class}）。不过RocketMQBinderAutoConfiguration时候的时候rocketMessageChannelBinder绑定到的nameServer来自于RocketMQBinderConfigurationProperties，就是spring.cloud.stream.rocketmq.binder，默认值127.0.0.1:9876表情包表情包表情包
 *</p>
 * @author wude
 * @date 2021/4/20 20:02
 */
@Configuration
@EnableBinding(RocketMessageProcessor.class)
public class RocketMessageConfig {
}