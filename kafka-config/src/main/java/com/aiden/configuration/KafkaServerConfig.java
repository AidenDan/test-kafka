package com.aiden.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

/**
 * @author Aiden
 * @version 1.0
 * @description kafka服务器配置
 * @date 2021-4-15 22:26:13
 */

@Configuration
@PropertySource(value = {"classpath:message-${spring.profiles.active}.properties"})
public class KafkaServerConfig {

    @Primary
    @Bean("kafkaProperties")
    @ConfigurationProperties("spring.kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }

    @Value("${spring.kafka.topic}")
    private String topics;

    /**
     * 创建topic
     * topic中创建5个分区 确保每一个分区对应一个消费者 每个分区3个副本
     *
     * @return topic
     */
    @Bean("test-topic")
    public NewTopic newTopic() {
        // 创建topic，需要指定创建的topic的"名称"、"分区数"、"副本数量(副本数数目设置要小于Broker数量)"
        return new NewTopic(topics, 2, (short) 1);
    }
}









