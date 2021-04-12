package com.zyz.study.lock;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程按顺序打印aliali
 * 使用lock控制同步，使用condition减少并发竞争
 *
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/5 16:59
 */
public class PrintInOrderLockAndCondition {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        String unitSentence = "ali";
        int loop = 3;

        char[] chars = unitSentence.toCharArray();
        PrinterLockAndCondition.cur = chars[0];
        PrinterLockAndCondition[] printers = new PrinterLockAndCondition[chars.length];
        Condition[] conditions = new Condition[chars.length];

        // 构造condition
        for (int i = 0; i < conditions.length; i++) {
            conditions[i] = lock.newCondition();
        }
        // 构造printer
        for (int i = 0; i < chars.length; i++) {
            char duty = chars[i];
            char nextChar = (i == chars.length - 1) ? chars[0] : chars[i+1];
            Condition nextCondition = (i == chars.length - 1) ? conditions[0] : conditions[i+1];
            printers[i] = new PrinterLockAndCondition(loop, duty, nextChar, conditions[i], nextCondition, lock);
        }
        // 启动printer
        for (PrinterLockAndCondition printer : printers) {
            new Thread(printer).start();
        }
    }
}

@SuppressWarnings("AlibabaLockShouldWithTryFinally")
class PrinterLockAndCondition implements Runnable {
    public PrinterLockAndCondition(int loop, char duty, char next, Condition waitCondition, Condition awakeCondition, Lock lock) {
        this.loop = loop;
        this.next = next;
        this.duty = duty;
        this.waitCondition = waitCondition;
        this.awakeCondition = awakeCondition;
        this.lock = lock;
    }

    public static volatile char cur;
    char duty;
    char next;
    int loop;
    Condition waitCondition;
    Condition awakeCondition;
    Lock lock;

    @Override
    public void run() {
        int i = 0;
        while (i < loop) {
            try {
                // 必须先获取锁
                lock.lock();
                if (duty != cur) {
                    waitCondition.wait();
                }
                System.out.print(duty);
                i++;
                cur = next;
                // 激活下一个
                awakeCondition.signal();
            } catch (Exception e) {
                // todo
            } finally {
                lock.unlock();
            }
        }
    }
}
