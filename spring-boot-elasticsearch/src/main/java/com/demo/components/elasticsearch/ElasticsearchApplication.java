package com.demo.components.elasticsearch;

import com.demo.components.elasticsearch.config.ESRestClientContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {RestClientAutoConfiguration.class})
public class ElasticsearchApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ElasticsearchApplication.class, args);
//        ((ConfigurableApplicationContext) ctx).close();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                ESRestClientContainer esRestClientContainer = ctx.getBean(ESRestClientContainer.class);
                esRestClientContainer.destroy();
            }
        }));

    }


}