package com.demo.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author wude
 * @date 2020/8/27 12:54
 */
@SpringBootApplication
public class HttpApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(HttpApplication.class, args);
    }

}