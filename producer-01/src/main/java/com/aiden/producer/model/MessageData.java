package com.aiden.producer.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-18 11:47:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "message")
public class MessageData {
    @TableId
    private String msgId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MsgStatus msgStatus;
    private int msgTimes = 1;
}
