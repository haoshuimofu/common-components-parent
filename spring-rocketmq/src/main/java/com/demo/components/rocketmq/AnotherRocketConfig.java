package com.demo.components.rocketmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2021/4/16 10:43
 */
@Component
public class AnotherRocketConfig {

    @Value("${another.rocketmq.topic.userTopic}")
    public String userTopic;

}