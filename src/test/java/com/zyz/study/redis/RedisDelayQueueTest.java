package com.zyz.study.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/26 17:54
 */
@SpringBootTest
class RedisDelayQueueTest {

    @Autowired
    private RedisDelayQueue redisDelayQueue;

    @Test
    void handle() {
        redisDelayQueue.delay("a", System.currentTimeMillis() + 5000);
        redisDelayQueue.delay("b", System.currentTimeMillis() + 6000);
        redisDelayQueue.delay("c", System.currentTimeMillis() + 7000);
        redisDelayQueue.delay("d", System.currentTimeMillis() + 8000);

        new Thread(() -> {
            // 在自己线程中运行
            redisDelayQueue.handle();
        }).start();

        new Thread(() -> {
            // 在自己线程中运行
            redisDelayQueue.handle();
        }).start();

        new Thread(() -> {
            // 在自己线程中运行
            redisDelayQueue.handle();
        }).start();

        new Thread(() -> {
            // 在自己线程中运行
            redisDelayQueue.handle();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}