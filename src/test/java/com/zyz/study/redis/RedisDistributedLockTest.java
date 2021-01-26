package com.zyz.study.redis;

import com.mysql.cj.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/26 10:22
 */
@SpringBootTest
class RedisDistributedLockTest {

    @Autowired
    private RedisDistributedLock lock;

    @Test
    void lock1() {
        // true
        System.out.println(lock.lock1("test", 1000));
        // false
        System.out.println(lock.lock1("test", 1000));
        try {
            // 等待锁过期
            TimeUnit.SECONDS.sleep(1);
            // true
            System.out.println(lock.lock1("test", 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void lock2() {
        System.out.println(lock.lock2("test"));
        try {
            TimeUnit.SECONDS.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.release2("test");
        System.out.println(lock.lock2("test"));
    }

    @Test
    void lockWithRetry() {
        System.out.println(lock.lock2("test"));
        // time out
        new Thread(() -> {
            try {
                System.out.println(lock.lockWithRetry("test", 10000, 1000));
            } catch (TimeoutException e) {
                System.out.println("lock timeout");
            }
        }).start();

        // success with retry
        new Thread(() -> {
            try {
                System.out.println(lock.lockWithRetry("test", 30000, 1000));
            } catch (TimeoutException e) {
                System.out.println("lock timeout");
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(20);
            lock.release2("test");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    void reentrantLock() {
        System.out.println(lock.reentrantLock("test"));
        System.out.println(lock.reentrantLock("test"));
        new Thread(() -> {
            System.out.println(lock.reentrantLock("test"));
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.reentrantLockRelease("test");
        lock.reentrantLockRelease("test");

    }
}