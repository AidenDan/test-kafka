package com.aiden.producer.service.impl;

import com.aiden.producer.dao.mapper.MessageMapper;
import com.aiden.producer.model.MessageData;
import com.aiden.producer.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-18 20:26:20
 */

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageData> implements MessageService {
}
