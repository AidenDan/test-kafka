package com.aiden.configuration;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-16 22:40:05
 */

@Configuration
public class KafkaProducerConsumerConfig {

    @Autowired
    @Qualifier("alphaKafkaProperties")
    public KafkaProperties alphaKafkaProperties;

    // 生产者配置
    @Bean("kafkaTemplate")
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(alphaKafkaProperties.buildProducerProperties());
    }

    // 单条消息消费者配置
    @Bean("singleKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> singleKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.getContainerProperties().setPollTimeout(3000);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        containerFactory.setConcurrency(1);
        return containerFactory;
    }

    // 消息批量消费者配置
    @Bean("batchKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> batchKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.getContainerProperties().setPollTimeout(3000);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        // 表示开启2个客户端监听消费消息
        containerFactory.setConcurrency(1);
        containerFactory.setBatchListener(true);
        return containerFactory;
    }

    public ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(alphaKafkaProperties.buildConsumerProperties());
    }
}
