package com.zyz.study.singleton;

/**
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/4 11:44
 */
public class Singleton {
    private Singleton() {
        // todo throw exp
    }
    public static class Holder {
        private static final Singleton singleton = new Singleton();
    }

    public static Singleton instance() {
        return Holder.singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> System.out.println(Singleton.instance().hashCode())).start();
        }
    }
}
