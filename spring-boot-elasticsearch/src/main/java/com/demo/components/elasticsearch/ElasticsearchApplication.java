package com.demo.components.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.ApplicationContext;

//@SpringBootApplication(exclude = {RestClientAutoConfiguration.class})
@SpringBootApplication(exclude = {ElasticsearchRestClientAutoConfiguration.class})
public class ElasticsearchApplication {

    public static void main(String[] args) {
        try {
            ApplicationContext ctx = SpringApplication.run(ElasticsearchApplication.class, args);
        } catch (Exception e) {
            System.exit(0);
        }
    }


}