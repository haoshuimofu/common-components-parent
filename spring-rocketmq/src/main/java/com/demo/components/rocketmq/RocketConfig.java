package com.demo.components.rocketmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2021/4/15 17:13
 */
@Component
public class RocketConfig {

    @Value("${rocketmq.topic.demoTopic}")
    public String demoTopic;

    @Value("${rocketmq.topic.userTopic}")
    public String userTopic;

}