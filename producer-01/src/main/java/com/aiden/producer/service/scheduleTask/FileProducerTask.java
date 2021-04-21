package com.aiden.producer.service.scheduleTask;

import com.aiden.UUIDGenerator;
import com.aiden.configuration.StringMessageProducer;
import com.aiden.producer.model.MessageData;
import com.aiden.producer.model.MsgStatus;
import com.aiden.producer.service.MessageService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-18 11:20:50
 */

@Slf4j
@Component
public class FileProducerTask {
    private static final HashMap<String, Object> map = new HashMap<>();

    @Autowired
    MessageService messageService;

    @Autowired
    StringMessageProducer stringMessageProducer;
    @Value("${spring.kafka.topic}")
    private String topic;

    // 每10秒执行一次该方法 生产消息
    @Scheduled(cron = "0/10 * * * * ?")
    public void scheduleMessage() {
        MessageData messageData = MessageData.builder()
                .msgId(UUIDGenerator.generator())
                .startTime(LocalDateTime.now())
                .msgStatus(MsgStatus.NONE)
                .build();

        // 发送消息前 将消息做持久化存储
        messageService.save(messageData);
        sendMsg(messageData);
    }

    private void sendMsg(MessageData messageData) {
        String msgDataString = JSON.toJSONString(messageData);
        stringMessageProducer
                .send(topic, msgDataString)
                .addCallback((stringStringSendResult) -> {
                    log.info("msgDataString:::{}", msgDataString);
                    // 如果这条消息发送成功，那么更新数据库中该条消息的状态为success
                    RecordMetadata recordMetadata = stringStringSendResult.getRecordMetadata();
                    boolean hasOffset = recordMetadata.hasOffset();
                    boolean hasTimestamp = recordMetadata.hasTimestamp();
                    long offset = recordMetadata.offset();
                    int partition = recordMetadata.partition();
                    long timestamp = recordMetadata.timestamp();
                    String topic = recordMetadata.topic();

                    map.put("hasOffset", hasOffset);
                    map.put("hasTimestamp", hasTimestamp);
                    map.put("offset", offset);
                    map.put("partition", partition);
                    map.put("timestamp", timestamp);
                    map.put("topic", topic);
                    log.info("map:::{}", map);

                    messageData.setMsgStatus(MsgStatus.SENT);
                    messageData.setTopic(topic);
                    messageData.setPartition(partition);
                    messageData.setOffset(offset);
                }, (throwable) -> {
                    // 如果这条消息发送失败, 那么更新数据库中该条消息状态为fail
                    messageData.setMsgStatus(MsgStatus.FAIL);
                    messageData.setExceptionMsg(throwable.getMessage());
                });

        // 更新发送消息的次数
        int msgTimes = messageService.getById(messageData.getMsgId()).getMsgTimes();
        messageData.setMsgTimes(msgTimes);
        messageService.updateById(messageData);
    }
}
