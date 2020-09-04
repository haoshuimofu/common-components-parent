package com.demo.components.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableBinding(SyncProduct2StationProcessor.class)
public class RabbitStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitStreamApplication.class, args);
    }

}