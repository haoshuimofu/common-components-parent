package com.demo.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@EnableRedisHttpSession  // == spring.session.store-type=redis
public class SpringSessionApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringSessionApplication.class, args);
        Object obj = ctx.getBean("springSessionRepositoryFilter");
        System.err.println(obj.getClass());
    }
}
