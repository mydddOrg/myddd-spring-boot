package org.myddd.commons.cache.redis;

import org.myddd.commons.cache.api.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.function.Supplier;

class RedisValueOperations<T> implements ValueOperations<T> {

    private Supplier<RedisTemplate<String,T>> supplier;

    private long duration;

    public RedisValueOperations(long duration,Supplier<RedisTemplate<String,T>> supplier){
        this.duration = duration;
        this.supplier = supplier;
    }

    private RedisTemplate<String,T> getRedisTemplate(){
        return supplier.get();
    }

    @Override
    public void put(String key, T value) {
        var redisTemplate = supplier.get();
        redisTemplate.opsForValue().set(key,value,duration);
    }

    @Override
    public T get(String key) {
        return getRedisTemplate().opsForValue().get(key);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(getRedisTemplate().hasKey(key));
    }

    @Override
    public void remove(String key) {
        getRedisTemplate().delete(key);
    }

    @Override
    public void clearAll() {
        var keys = getRedisTemplate().keys("*");
        if(Objects.nonNull(keys)) getRedisTemplate().delete(keys);
    }

}
