package org.myddd.commons.cache.guava;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.ValueOperations;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GuavaCache<T> implements Cache<T> {

    @Override
    public ValueOperations<T> opsForValue() {
        return guavaValueOperation;
    }

    private class GuavaValueOperations implements ValueOperations<T> {

        private com.google.common.cache.Cache<String, T> cache;

        GuavaValueOperations(long duration, long maximumSize) {
            this.cache = CacheBuilder.newBuilder()
                    .maximumSize(maximumSize)
                    .expireAfterAccess(duration, TimeUnit.MINUTES)
                    .build();
        }


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

    private GuavaValueOperations guavaValueOperation;

    public GuavaCache(long duration,long maximumSize){
        this.guavaValueOperation = new GuavaValueOperations(duration,maximumSize);
    }

}

