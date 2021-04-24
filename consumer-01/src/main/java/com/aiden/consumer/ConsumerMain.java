package com.aiden.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-24 11:34:15
 */

@PropertySource(value = {"classpath:consumer-${spring.profiles.active}.properties"})
@ComponentScan(basePackages = {"com.aiden"})
@SpringBootApplication
public class ConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class);
    }
}
