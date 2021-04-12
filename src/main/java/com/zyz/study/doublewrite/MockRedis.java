package com.zyz.study.doublewrite;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/9 13:56
 */
@Slf4j
public class MockRedis {
    private Map<String, String> data = new HashMap<>();

    public Boolean put(String k, String v) {
        log.info("insert/update redis, k={}, v={}", k, v);
        data.put(k, v);
        return true;
    }

    public String get(String k) {
        String v = data.get(k);
        log.info("get from redis, k={}, v={}", k, v);
        return v;
    }

    public Boolean del(String k) {
        log.info("remove from redis, k={}", k);
        data.remove(k);
        return true;
    }
}
