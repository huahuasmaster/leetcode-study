package com.zyz.study.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * 基于Redis的分布式锁
 *
 * @author 执零
 * @version 1.0
 * @date 2021/1/25 17:29
 */
@Component
@Slf4j
public class RedisDistributedLock {

    /**
     * 记录当前线程的加锁情况
     */
    ThreadLocal<HashMap<String, Integer>> stateMap = ThreadLocal.withInitial(HashMap::new);

    /**
     * 管理看门狗的线程池
     * 思考：设为守护线程能解决什么问题？对于当前场景(很多工作线程处于睡眠状态），线程池该如何调优？
     */
    ExecutorService exec = Executors.newCachedThreadPool(runnable -> {
        Thread t = Executors.defaultThreadFactory().newThread(runnable);
        t.setDaemon(true);
        t.setName("Watch Dog : " + t.getId());
        return t;
    });

    @PreDestroy
    public void preDestroy() {
        log.info("dogs prepare to stop working");
        // shutdownNow会调用一遍所有worker的interrupt
        exec.shutdownNow();
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 记录看门狗线程
     */
    private final ConcurrentHashMap<String, Future> dogs = new ConcurrentHashMap<>();

    /**
     * 最基本的锁
     * nx - 锁的互斥
     * ex - 防止锁一直存在，造成死锁
     * 缺点：业务超时问题：如果业务线程获取锁后执行了很长时间，此时锁自动释放，其他线程能获取到锁，会有并发问题
     *
     * @param key
     * @param expireTime
     * @return
     */
    public boolean lock1(String key, long expireTime) {
        // set k v ex {expireTime} nx 如果key不存在则赋值，赋值成功代表获取锁
        return redisTemplate.opsForValue().setIfAbsent(key, 1, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 释放锁
     *
     * @param key
     */
    public void release1(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 上一个方案的改进版，过期时间定死为30s，另起线程每过10S刷新。
     * 难点在于子线程的控制
     *
     * @param key
     * @return
     */
    public boolean lock2(final String key) {
        if (redisTemplate.opsForValue().setIfAbsent(key, 1, 30, TimeUnit.SECONDS)) {
            WatchDog watchDog = new WatchDog(key, redisTemplate);
            Future<?> dog = exec.submit(watchDog);
            dogs.put(key, dog);
            return true;
        }
        return false;
    }

    /**
     * 先中断子线程，然后释放锁
     *
     * @param key
     */
    public void release2(String key) {
        Future future = dogs.get(key);
        if (future != null) {
            // 中断看门狗
            future.cancel(true);
        }
        dogs.remove(key);
        redisTemplate.delete(key);
    }

    /**
     * 可重入：减轻redis压力；防止出现死锁；
     * 原理在于使用ThreadLocal记录当前线程获取的锁
     *
     * @param key
     * @return
     */
    public boolean reentrantLock(final String key) {
        // 先检查是否获取了锁
        Integer state = stateMap.get().get(key);
        // 此变量是线程隔离的，不会出现并发现象
        if (state != null && state > 0) {
            // 可重入
            state += 1;
            stateMap.get().put(key, state);
            return true;
        }
        // 尝试加锁
        if (lock2(key)) {
            stateMap.get().put(key, 1);
            return true;
        }

        return false;
    }

    public void reentrantLockRelease(String key) {
        // state--
        Integer state = stateMap.get().get(key);
        state -= 1;
        stateMap.get().put(key, state);

        // 真正释放锁
        if (state == 0) {
            release2(key);
        }
    }

    /**
     * 带重试和超时机制的锁
     * 思考：使用sleep还是有点被动，有没有什么机制能在锁释放时主动唤醒
     *
     * @param key
     * @param waitTime      等待时长
     * @param retryDuration 重试间隔
     * @return
     * @throws TimeoutException 超出等待时长时抛错
     */
    public boolean lockWithRetry(String key, long waitTime, long retryDuration) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        boolean success = lock2(key);
        while (!success) {
            if (System.currentTimeMillis() - startTime > waitTime) {
                throw new TimeoutException();
            }

            try {
                TimeUnit.MILLISECONDS.sleep(retryDuration);
                success = lock2(key);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}

/**
 * 看门狗
 * 负责定时重置锁的超时时间
 * 解决了"获取到锁的线程还在运行中，但是锁自动过期，其他线程接着获取到锁"的问题
 */
@Slf4j
@AllArgsConstructor
class WatchDog implements Runnable {
    private String key;
    private RedisTemplate redisTemplate;

    @Override
    public void run() {
        log.info("dog begin work, key = {}", key);
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.info("dog's job has done， key = {}", key);
                return;
            }
            log.info("reset expire of {}", key);
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
        }
    }
}
