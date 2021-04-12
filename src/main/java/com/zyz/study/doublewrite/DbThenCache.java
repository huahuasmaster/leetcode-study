package com.zyz.study.doublewrite;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 先删除DB中的数据，后删除缓存
 * 问题：假设缓存一开始没塞值，如果有两个线程同时进行读写操作，可能出现旧数据被写入缓存，最终导致问题
 * 线程1         线程2     线程3
 * 读库
 *              改库
 *              删缓存
 * 旧数据写入缓存
 *                      读到旧数据
 *
 * 解决方案一
 * 写操作在删除缓存之后，延迟一段时间再删除一遍
 * 线程1         线程2     线程3
 * 读库
 *              改库
 *              删缓存
 * 旧数据写入缓存
 *              再删一遍缓存
 *                      读到新数据
 *
 * +实现简单，能保证最终一致性
 * -可能有短暂时间的不一致，甚至发生读线程阻塞时间过长，在延迟删除之后再写入旧数据的情况。所以需要仔细考虑延迟时间
 * 线程1         线程2     线程3
 * 读库
 *              改库
 *              删缓存
 *              （过了一段时间）
 *              再删一遍缓存
 * 旧数据写入缓存
 *                      读到旧数据
 *
 * 解决方案二
 * 使用分布式读写锁
 * 当有写操作存在时，新来的读写操作会被阻塞等待写操作执行成功。
 * 否则，读操作可以直接读取，写操作同样会阻塞。
 * +能彻底解决问题
 * -引入了额外的中间件，可靠性下降。
 * -性能下降
 *
 * 解决方案三
 * 结合数据版本号，删除缓存时做逻辑删除
 */
@Slf4j
public class DbThenCache implements Cache {

    static MockDB mockDB = new MockDB();
    static MockRedis mockRedis = new MockRedis();

    @Override
    public String get(String k) {
        String v = mockRedis.get(k);
        if (v == null) {
            v = mockDB.select(k);
            log.info("模拟获取db数据后发生阻塞1S");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mockRedis.put(k, v);
        }
        return v;
    }

    @Override
    public Boolean update(String k, String v) {
        mockDB.insert(k, v);
        mockRedis.del(k);
        // 延迟删除策略，可以保证最终一致性，但是会有短暂的不一致
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("延迟删除cache");
            mockRedis.del(k);
        }, "延迟删除线程").start();
        return true;
    }

    public static void main(String[] args) {
        DbThenCache dbThenCache = new DbThenCache();

        dbThenCache.update("name", "old");
        Thread firstGetThread = new Thread(() -> {
            // 此时缓存中没有，会读库，并发生1s阻塞
            log.info(dbThenCache.get("name"));
        }, "读库发生阻塞的读线程");

        Thread getOldDataThread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
                // 此时读取缓存，可能读到了旧数据
                log.info(dbThenCache.get("name"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "读取到旧数据的线程");

        Thread getNewDataThread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
                // 此时读到了最终一致的数据
                log.info(dbThenCache.get("name"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "读取到新数据的线程");

        Thread writeThread = new Thread(() -> {
            dbThenCache.update("name", "new");
        }, "写线程");
        // 读写同时启动，模拟读线程发生阻塞，唤醒后向缓存写入旧数据的问题。
        firstGetThread.start();
        getOldDataThread.start();
        getNewDataThread.start();
        writeThread.start();
    }
}


