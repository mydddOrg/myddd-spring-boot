package org.myddd.commons.cache.guava;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import org.myddd.commons.cache.api.Cache;

import javax.inject.Named;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Named
public class GuavaCache<T> implements Cache<T> {

    private com.google.common.cache.Cache<String, T> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    @Override
    public void put(String key, T value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        cache.put(key,value);
    }

    @Override
    public T get(String key) {
        Preconditions.checkNotNull(key);

        return cache.getIfPresent(key);
    }

    @Override
    public boolean exists(String key) {
        return !Objects.isNull(cache.getIfPresent(key));
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }

    @Override
    public void clearAll() {
        cache.invalidateAll();
    }
}

