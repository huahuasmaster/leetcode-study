package com.zyz.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于zset实现延迟队列
 * zrangebyscore test 0 600 LIMIT 0 1 获取到期的一条记录
 * zrem test task1 删除成功的才有资格获取执行
 * 轮询持续获取
 * @author 执零
 * @version 1.0
 * @date 2021/1/26 17:29
 */
@Component
@Slf4j
public class RedisDelayQueue {

    private String queueName = "delay";

    @Autowired
    private RedisTemplate redisTemplate;

    public void delay(String s, long targetTime) {
        log.info("put {}, exe at {}", s, targetTime);
        redisTemplate.opsForZSet().add(queueName, s, targetTime);
    }

    public void handle() {
        for (;;) {
            Set set = redisTemplate.opsForZSet().rangeByScore(queueName, 0, System.currentTimeMillis(), 0, 1);
            if (CollectionUtils.isEmpty(set)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String s = (String) set.iterator().next();
            log.info("get job {}", s);
            if (redisTemplate.opsForZSet().remove(queueName, s) == 1) {
                // 消费
                log.info("{} at {}",s, System.currentTimeMillis());
            }

        }

    }
}
