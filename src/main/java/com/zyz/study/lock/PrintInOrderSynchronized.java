package com.zyz.study.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/5 18:17
 */
public class PrintInOrderSynchronized {

}
class PrinterSynchronized implements Runnable {

    static char cur;
    char duty;
    int loop;

    @Override
    public void run() {

    }
}
