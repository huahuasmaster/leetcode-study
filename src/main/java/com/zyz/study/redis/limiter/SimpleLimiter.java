package com.zyz.study.redis.limiter;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.LongHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 基于时间窗口实现的限流算法
 * 缺点：对于60s内限制访问100w次这种场景，不能很好的支持，太占内存了
 * 这个问题可以通过漏斗算法解决
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
        List<List> list = redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            // 开启事务，防止保证指令连续执行 类似begin
            redisConnection.multi();
            // 存入redis zadd key score value
            redisConnection.zAdd(key, now, String.valueOf(now).getBytes(StandardCharsets.UTF_8));
            long min = now - timeWindowSeconds * 1000;
            // 时间窗口之外的直接删除 zremrangebyscore key min max
            redisConnection.zRemRangeByScore(key, 0, min);
            // 查询出当前剩下的记录 zcard key
            Long curCount = redisConnection.zCard(key);
            // 重置过期时间，这里的参数是秒,多宽限一秒
            redisConnection.expire(key, timeWindowSeconds + 1);
            // 类似commit
            redisConnection.exec();
            return null;
        });
        log.info(JSONUtil.toJsonStr(list));
        return ((long)list.get(0).get(2)) <= limitCount;
    }
}
