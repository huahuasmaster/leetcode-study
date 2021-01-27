package com.zyz.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/26 20:07
 */
@SpringBootTest
@Slf4j
class HyperLogLogTest {

    @Autowired
    private HyperLogLog hyperLogLog;

    @Test
    void count() {
        for (int i = 0;i < 10000;i++) {
            hyperLogLog.add("user" + i);
        }

        log.info("hyper count = {}", hyperLogLog.count());
    }
}