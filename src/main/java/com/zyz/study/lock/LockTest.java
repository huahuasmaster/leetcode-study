package com.zyz.study.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/9 13:07
 */
public class LockTest {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);
        Printer1 p1 = new Printer1(lock, 'a', true);
        Printer1 p2 = new Printer1(lock, 'b', true);
        Printer1 p3 = new Printer1(lock, 'c', true);
        Printer1 p4 = new Printer1(lock, 'd', true);
        Printer1 p5 = new Printer1(lock, 'e', true);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }

}

class Printer1 extends Thread {
    Lock lock;
    char duty;
    boolean isHead;

    public Printer1(Lock lock, char duty, boolean isHead) {
        this.lock = lock;
        this.duty = duty;
        this.isHead = isHead;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5;i++) {
            if (i == 0 && isHead) {
                lock.lock();
            }
            System.out.println(duty);
            lock.unlock();
        }


    }
}
