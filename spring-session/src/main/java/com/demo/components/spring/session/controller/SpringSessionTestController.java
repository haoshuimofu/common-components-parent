package com.demo.components.spring.session.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/10/14 11:32
 */
@RestController
@RequestMapping("/spring/session/test")
public class SpringSessionTestController {

    @RequestMapping("hello")
    public Object hello() {
        return "hello";
    }

}