package com.zyz.study.doublewrite;

/**
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/9 13:50
 */
public interface Cache {
    String get(String k);
    Boolean update(String k, String v);
}
