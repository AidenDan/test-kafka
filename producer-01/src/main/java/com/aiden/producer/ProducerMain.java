package com.aiden.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-16 21:22:51
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.aiden"})
@MapperScan(value = {"com.aiden.producer.dao"})
public class ProducerMain {

    public static void main(String[] args) {
        SpringApplication.run(ProducerMain.class);
    }
}
