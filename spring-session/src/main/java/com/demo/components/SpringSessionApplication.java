package com.demo.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@EnableRedisHttpSession  // 等同于在配置里添加配置: spring.session.store-type=redis
public class SpringSessionApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringSessionApplication.class, args);
        Object obj = ctx.getBean("springSessionRepositoryFilter");
        /**
         * {@link org.springframework.session.web.http.SessionRepositoryFilter}
         */
        System.err.println(obj.getClass());
    }
}
