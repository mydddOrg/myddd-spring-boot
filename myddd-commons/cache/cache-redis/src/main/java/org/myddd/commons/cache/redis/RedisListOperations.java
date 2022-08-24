package org.myddd.commons.cache.redis;

import org.myddd.commons.cache.api.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class RedisListOperations<T> implements ListOperations<T> {

    private Supplier<RedisTemplate<String,T>> supplier;

    private long duration;

    public RedisListOperations(long duration,Supplier<RedisTemplate<String,T>> supplier){
        this.duration = duration;
        this.supplier = supplier;
    }


    @Override
    public List<T> range(String key, long start, long end) {
        return null;
    }

    @Override
    public void trim(String key, long start, long end) {

    }

    @Override
    public Long size(String key) {
        return null;
    }

    @Override
    public Long leftPush(String key, T value) {
        return null;
    }

    @Override
    public Long leftPushAll(String key, T... values) {
        return null;
    }

    @Override
    public Long leftPushAll(String key, Collection<T> values) {
        return null;
    }

    @Override
    public Long leftPushIfPresent(String key, T value) {
        return null;
    }

    @Override
    public Long leftPush(String key, T pivot, T value) {
        return null;
    }

    @Override
    public Long rightPush(String key, T value) {
        return null;
    }

    @Override
    public Long rightPushAll(String key, T... values) {
        return null;
    }

    @Override
    public Long rightPushAll(String key, Collection<T> values) {
        return null;
    }

    @Override
    public Long rightPushIfPresent(String key, T value) {
        return null;
    }

    @Override
    public Long rightPush(String key, T pivot, T value) {
        return null;
    }
}

