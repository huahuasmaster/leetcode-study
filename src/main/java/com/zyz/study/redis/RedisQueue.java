package com.zyz.study.redis;

import ch.qos.logback.core.util.TimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Time;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/26 16:42
 */
@AllArgsConstructor
public class RedisQueue<T> implements Queue<T> {

    private String queueName;

    private RedisTemplate redisTemplate;

    @Override
    public int size() {
        // llen k
        Long size = redisTemplate.opsForList().size(queueName);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public boolean isEmpty() {
        return size() > 0;
    }

    @Override
    public boolean contains(Object o) {
        // lpos k
        return redisTemplate.opsForList().indexOf(queueName, o) > 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return redisTemplate.opsForList().range(queueName, 1, -1).toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        // lpush k
        return redisTemplate.opsForList().leftPush(queueName, t) > 0;
    }

    @Override
    public boolean remove(Object o) {

        // lrem k
        return redisTemplate.opsForList().remove(queueName, 1, o) > 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return redisTemplate.opsForList().leftPushAll(queueName, c) == c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        redisTemplate.delete(queueName);
    }

    @Override
    public boolean offer(T t) {
        return redisTemplate.opsForList().leftPush(queueName, t) > 0;
    }

    @Override
    public T remove() {
        return (T) redisTemplate.opsForList().rightPop(queueName, 99, TimeUnit.DAYS);
    }

    @Override
    public T poll() {
        return (T) redisTemplate.opsForList().rightPop(queueName);
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return (T) redisTemplate.opsForList().index(queueName, -1);
    }
}
