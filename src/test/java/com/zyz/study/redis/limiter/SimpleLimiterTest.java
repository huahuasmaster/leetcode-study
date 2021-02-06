package com.zyz.study.redis.limiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/28 10:16
 */
@SpringBootTest
@Slf4j
class SimpleLimiterTest {

    @Autowired
    private SimpleLimiter simpleLimiter;

    @Test
    void pass() {
        for (int i = 0; i < 10; i++) {
            System.out.println(simpleLimiter.pass(1L, "buy", 5L, 5));
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(simpleLimiter.pass(1L, "buy", 5L, 5));
        }

    }
}