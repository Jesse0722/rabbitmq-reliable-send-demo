package com.github.jesse0722.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lijiajun
 * @date 2020/11/09 16:21
 */
@SpringBootApplication
@EnableScheduling
public class RabbitmqDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }
}
