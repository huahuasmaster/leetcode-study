package com.zyz.study.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/26 18:49
 */
@Component
public class HyperLogLog {
    @Autowired
    private RedisTemplate redisTemplate;

    String name = "hll";

    public void add(String s) {
        redisTemplate.opsForHyperLogLog().add(name, s);
    }

    public long count() {
        return redisTemplate.opsForHyperLogLog().size(name);
    }


}
