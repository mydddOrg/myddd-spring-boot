package org.myddd.commons.cache.redis;

import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.ListOperations;
import org.myddd.commons.cache.api.ValueOperations;
import org.myddd.domain.InstanceFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RedisCache<T> implements Cache<T> {

    private RedisTemplate<String,T> redisTemplate;

    private long duration;

    private RedisValueOperations<T> valueOperation;

    private RedisListOperations<T> listOperations;

    public RedisCache(long duration,long maximumSize){
        this.duration = duration;
        this.valueOperation = new RedisValueOperations<>(duration, this::getRedisTemplate);
        this.listOperations = new RedisListOperations<>(duration,this::getRedisTemplate);
    }

    private RedisTemplate<String,T> getRedisTemplate(){
        if(Objects.isNull(redisTemplate)){
            var connectionFactory = InstanceFactory.getInstance(RedisConnectionFactory.class);
            redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connectionFactory);

            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
            redisTemplate.afterPropertiesSet();
        }
        return this.redisTemplate;
    }

    @Override
    public ValueOperations<T> opsForValue() {
        return valueOperation;
    }

    @Override
    public ListOperations<T> optForList() {
        return listOperations;
    }
}
