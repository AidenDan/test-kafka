package com.aiden.consumer;

import com.aiden.consumer.util.ThreadPool;
import com.aiden.dao.mapper.MessageMapper;
import com.aiden.model.MessageData;
import com.aiden.model.MsgStatus;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-24 11:31:15
 */

@Slf4j
@Component
public class MessageConsumer {
    private static final String BATCH_LISTEN_ID = "batch-listen-id";
    private MessageMapper messageMapper;
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    public MessageConsumer(MessageMapper messageMapper, KafkaListenerEndpointRegistry registry) {
        this.messageMapper = messageMapper;
        this.registry = registry;
    }

    // 60s内必须轮询一次消息, 否则kafka会认为此消费者宕机, 将其分区分给其他消费者
    @KafkaListener(id = BATCH_LISTEN_ID,
            topics = {"${alpha.spring.kafka.topic}"},
            containerFactory = "batchKafkaListenerContainerFactory",
            groupId = "${alpha.spring.kafka.consumer.group-id}",
            properties = {"max.poll.interval.ms:60000", "max.poll.records=50", "auto.offset.reset=latest"})
    public void batchListen(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {
        MessageListenerContainer listenerContainer = registry.getListenerContainer(BATCH_LISTEN_ID);

        // 轮询一次消息后就暂停轮询, 等待所有的消息消费完毕再去轮询新的一批消息
        listenerContainer.pause();
        log.info("messageSize:{}", records.size());
        // 什么时候手动提交ack
        // 并发消费量=客户端数*开启的线程数
        CountDownLatch countDownLatch = new CountDownLatch(records.size());
        ArrayBlockingQueue<Runnable> messageBlockingQueue = ThreadPool.getMessageBlockingQueue();
        log.info("messageBlockingQueueSize:{}", messageBlockingQueue.size());
        ExecutorService threadPool = ThreadPool.getThreadPool();
        records.forEach(record -> {
            threadPool.submit(() -> {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 处理消息的主要逻辑
                        try {
                            String msgValue = record.value();
                            Optional<String> optionalMsg = Optional.ofNullable(msgValue);
                            optionalMsg.ifPresent((msg) -> {
                                MessageData messageData = JSON.parseObject(msg, MessageData.class);
                                messageData.setMsgStatus(MsgStatus.SUCCESS);
                                messageData.setEndTime(LocalDateTime.now());
                                // 更新消息状态
                                messageMapper.updateById(messageData);
                                log.info(msg);
                            });
                        } catch (Exception e) {
                            log.error("deal message exception:{}", e.getMessage());
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("countDownLatch exception:{}", e.getMessage());
        }

        // 手动提交一批消息ack
        acknowledgment.acknowledge();
        // 所有的消息都已经消费完毕了, 重新开始消费消息
        listenerContainer.resume();
        log.info("finish commit ack manual");
    }
}
