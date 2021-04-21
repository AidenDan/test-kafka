package com.aiden;

import java.util.UUID;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-21 22:15:36
 */

public class UUIDGenerator {
    public static String generator(){
        return UUID.randomUUID().toString();
    }
}
