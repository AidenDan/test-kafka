package com.aiden.producer.model;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-18 14:13:28
 */
public enum MsgStatus {
    NONE,
    FAIL,
    SENT,
    SUCCESS;

    private MsgStatus(){}
}
