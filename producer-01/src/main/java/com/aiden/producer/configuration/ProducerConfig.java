package com.aiden.producer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-18 09:35:57
 */

@EnableScheduling
@Configuration
@PropertySource(value = {"classpath:producer-${spring.profiles.active}.properties", "db-${spring.profiles.active}.properties"})
public class ProducerConfig {
}
