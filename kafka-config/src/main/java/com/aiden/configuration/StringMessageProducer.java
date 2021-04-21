package com.aiden.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-18 11:24:11
 */

@Component
public class StringMessageProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public ListenableFuture<SendResult<String, String>> send(String topic, String data) {
        return kafkaTemplate.send(topic, data);
    }
}
