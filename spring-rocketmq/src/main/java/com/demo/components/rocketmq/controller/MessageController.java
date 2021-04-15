package com.demo.components.rocketmq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2021/4/15 15:53
 */
@RestController
@RequestMapping("message")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping("send")
    public Object send() {
        return "send ok";
    }

}