package com.demo.components.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableBinding(SyncProduct2StationProcessor.class)
public class RabbitStreamMultiBinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitStreamMultiBinderApplication.class, args);
    }

}