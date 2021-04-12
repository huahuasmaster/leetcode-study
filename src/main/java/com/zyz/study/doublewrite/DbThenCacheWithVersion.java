package com.zyz.study.doublewrite;

/**
 * 使用版本号，解决读线程往缓存中写入旧数据
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/9 16:44
 */
public class DbThenCacheWithVersion implements Cache {

    @Override
    public String get(String k) {
        return null;
    }

    @Override
    public Boolean update(String k, String v) {
        return null;
    }
}
