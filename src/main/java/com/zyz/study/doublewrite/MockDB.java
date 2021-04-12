package com.zyz.study.doublewrite;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/9 13:51
 */
@Slf4j
public class MockDB {
    private Map<String, String> data = new HashMap<>();

    public int insert(String k, String v) {
        log.info("insert/update db, k={}, v={}", k, v);
        data.put(k, v);
        return 1;
    }

    public String select(String k) {
        String v = data.get(k);
        log.info("get from db, k={}, v={}", k, v);
        return v;
    }
}
