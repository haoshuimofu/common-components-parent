package com.demo.components.elasticsearch;

import com.demo.components.elasticsearch.config.ESRestClientContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {RestClientAutoConfiguration.class})
public class ElasticsearchApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ElasticsearchApplication.class, args);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                ESRestClientContainer esRestClientContainer = ctx.getBean(ESRestClientContainer.class);
                esRestClientContainer.destroy();
            }
        }));

    }


}