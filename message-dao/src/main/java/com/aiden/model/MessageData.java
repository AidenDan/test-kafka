package com.aiden.model;

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
    // 消息的发送时间
    private LocalDateTime startTime;
    // 消息被消费时时间
    private LocalDateTime endTime;
    // 消息的状态
    private MsgStatus msgStatus;
    // 消息发送成功时在分区中的offset
    private Long msgOffset;
    // 消息发送成功时所在的分区
    private Integer msgPartition;
    // 消息发送成功时所发送的主题
    private String topic;
    // 发送失败时的异常信息
    private String exceptionMsg;
    // 消息的发送次数
    private int msgTimes;
}
