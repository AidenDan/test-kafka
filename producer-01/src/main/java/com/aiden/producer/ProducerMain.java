package com.aiden.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-16 21:22:51
 */

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.aiden"})
@MapperScan(value = {"com.aiden.producer.dao"})
@PropertySource(value = {"classpath:producer-${spring.profiles.active}.properties", "db-${spring.profiles.active}.properties"})
public class ProducerMain {

    public static void main(String[] args) {
        SpringApplication.run(ProducerMain.class);
    }
}
