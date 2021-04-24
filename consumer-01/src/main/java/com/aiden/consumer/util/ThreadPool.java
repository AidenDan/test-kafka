package com.aiden.consumer.util;

import java.util.concurrent.*;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-24 13:04:18
 */
public class ThreadPool {
    private static final ArrayBlockingQueue<Runnable> messageBlockingQueue = new ArrayBlockingQueue<Runnable>(1000);

    private static final ExecutorService threadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, messageBlockingQueue);

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static ArrayBlockingQueue<Runnable> getMessageBlockingQueue() {
        return messageBlockingQueue;
    }
}
