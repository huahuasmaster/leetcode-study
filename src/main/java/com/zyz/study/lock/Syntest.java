package com.zyz.study.lock;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/31 12:09
 */
public class Syntest {
    public static void main(String[] args) {
        // 最节省空间的做法，只有一个对象头
        Object[] lock = new Object[0];

        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                synchronized (lock) {
                    System.out.println("t1:"+i);
                    try {
                        Thread.sleep(6000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 释放锁
//                    lock.notifyAll();
//                    // 自己阻塞
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getId()+":"+i);
                    // 释放锁
                    lock.notifyAll();
                    // 自己阻塞
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
