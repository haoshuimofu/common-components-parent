package com.demo.components.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 性能排序：fanout > direct >> topic。比例大约为11：10：6
 */
@SpringBootApplication
public class RabbitmqApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplicationTest.class, args);
    }
}
