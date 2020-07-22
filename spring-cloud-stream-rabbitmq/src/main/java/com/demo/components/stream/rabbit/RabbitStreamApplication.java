package com.demo.components.stream.rabbit;

import com.demo.components.stream.rabbit.message.pollable.SyncProduct2StationProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(SyncProduct2StationProcessor.class)
public class RabbitStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitStreamApplication.class, args);
    }

}