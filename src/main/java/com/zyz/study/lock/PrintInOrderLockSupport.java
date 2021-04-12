package com.zyz.study.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 朱云壮(执零)
 */
public class PrintInOrderLockSupport {
    public static void main(String[] args) {
        final int nums = 3;
        Thread aPrinter = new Thread(() -> {
            for (int i = 0;i < nums; i++) {
                LockSupport.park();
                System.out.print('a');
            }
        });
        Thread lPrinter = new Thread(() -> {
            for (int i = 0;i < nums; i++) {
                LockSupport.park();
                System.out.print('l');
            }
        });
        Thread iPrinter = new Thread(() -> {
            for (int i = 0;i < nums; i++) {
                LockSupport.park();
                System.out.print('i');
            }
        });
        aPrinter.start();
        lPrinter.start();
        iPrinter.start();
        for (int i = 0;i < nums; i++) {
            LockSupport.unpark(aPrinter);
            LockSupport.unpark(lPrinter);
            LockSupport.unpark(iPrinter);
        }
    }

}
