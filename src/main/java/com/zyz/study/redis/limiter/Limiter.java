package com.zyz.study.redis.limiter;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/27 17:52
 */
public interface Limiter {
    /**
     *
     * @param userId
     * @param action
     * @param timeWindowSeconds 时间窗口
     * @param limitCount 在时间窗口内的最大执行次数
     * @return true-can go on
     */
    boolean pass(Long userId, String action, Long timeWindowSeconds, Integer limitCount);
}
