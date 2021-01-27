package com.zyz.study.redis.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/27 17:55
 */
@Component
@Slf4j
public class SimpleLimiter implements Limiter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean pass(Long userId, String action, Long timeWindowSeconds, Integer limitCount) {
        long now = System.currentTimeMillis();
        byte[] key = String.format("limit:%s:%s", userId, action).getBytes(StandardCharsets.UTF_8);



        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.multi();
            // 存入redis zadd key score value
            redisConnection.zAdd(key, now, String.valueOf(now).getBytes(StandardCharsets.UTF_8));
            // 时间窗口之外的直接删除
            long min = now - timeWindowSeconds * 1000;
            redisConnection.zRemRangeByScore(key, 0, min);
            // 查询出当前剩下的记录
            Long curCount;
            curCount = redisConnection.zCard(key);
            // 重置过期时间，这里的参数是秒,多宽限一秒
            redisConnection.expire(key, timeWindowSeconds + 1);
            redisConnection.exec();
            return null;
        });
        return true;
    }
}
