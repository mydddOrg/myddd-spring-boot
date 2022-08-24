package org.myddd.commons.cache.redis;

import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.ValueOperation;
import org.myddd.domain.InstanceFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

public class RedisCache<T> implements Cache<T> {

    private RedisTemplate<String,T> redisTemplate;

    private long duration;

    private RedisValueOperation valueOperation;

    public RedisCache(long duration,long maximumSize){
        this.duration = duration;
        this.valueOperation = new RedisValueOperation();
    }

    private RedisTemplate<String,T> getRedisTemplate(){
        if(Objects.isNull(redisTemplate)){
            var connectionFactory = InstanceFactory.getInstance(RedisConnectionFactory.class);
            redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connectionFactory);

            var stringRedisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setValueSerializer(stringRedisSerializer);
            redisTemplate.afterPropertiesSet();
        }
        return this.redisTemplate;
    }

    @Override
    public ValueOperation<T> valueOperation() {
        return valueOperation;
    }

    private class RedisValueOperation implements ValueOperation<T> {


        @Override
        public void put(String key, T value) {
            getRedisTemplate().opsForValue().set(key,value,duration);
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
}
