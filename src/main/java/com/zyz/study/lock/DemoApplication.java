package com.zyz.study.lock;


import java.util.concurrent.locks.LockSupport;

public class DemoApplication {

    Thread t1;
    Thread t2;
    Thread t3;
    Thread t4;
    Thread t5;
    char current = 'e';


    public void run() {
        t1 = new Thread(() -> {
            int count = 0;
            while (count < 5) {
                if (current != 'e') {
                    LockSupport.park();
                }
                System.out.println('a');
                current = 'a';
                LockSupport.unpark(t2);
                count++;
            }
        });

        t2 = new Thread(() -> {
            int count = 0;
            while (count < 5) {
                if (current != 'a') {
                    LockSupport.park();
                }
                System.out.println('b');
                current = 'b';
                count++;
                LockSupport.unpark(t3);

            }
        });

        t3 = new Thread(() -> {
            int count = 0;

            while (count < 5) {
                if (current != 'b') {
                    LockSupport.park();
                }
                System.out.println('c');
                current = 'c';
                count++;
                LockSupport.unpark(t4);
            }
        });

        t4 = new Thread(() -> {
            int count = 0;

            while (count < 5) {
                if (current != 'c') {
                    LockSupport.park();
                }
                System.out.println('d');
                current = 'd';
                count++;
                LockSupport.unpark(t5);
            }
        });

        t5 = new Thread(() -> {
            int count = 0;
            while (count < 5) {
                if (current != 'd') {
                    LockSupport.park();
                }
                System.out.println('e');
                current = 'e';
                count++;
                LockSupport.unpark(t1);
            }
        });


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


    }


    public static void main(String[] args) {
        DemoApplication demoApplication = new DemoApplication();
        demoApplication.run();
    }


}

class Printer extends Thread {
    char pre;
    char duty;
    int count;
    Character current;

    public Printer(char pre, char duty, Character current, int count) {
        this.pre = pre;
        this.duty = duty;
        this.count = count;
        this.current = current;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            if (current != 'd') {
                LockSupport.park();
            }
            System.out.println('e');
            current = 'e';
            count++;
//            LockSupport.unpark(t1);
        }

    }
}
