package com.demo.components.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {RestClientAutoConfiguration.class})
public class ElasticsearchApplication {

    public static void main(String[] args) {
        try {
            ApplicationContext ctx = SpringApplication.run(ElasticsearchApplication.class, args);
        } catch (Exception e) {
            System.exit(0);
        }
    }


}