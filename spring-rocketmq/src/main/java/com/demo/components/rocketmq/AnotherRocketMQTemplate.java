package com.demo.components.rocketmq;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * @author wude
 * @date 2021/4/16 10:38
 */
@ExtRocketMQTemplateConfiguration(
        nameServer = "${another.rocketmq.name-server}",
        group = "${another.rocketmq.producer.group}")
public class AnotherRocketMQTemplate extends RocketMQTemplate {
}