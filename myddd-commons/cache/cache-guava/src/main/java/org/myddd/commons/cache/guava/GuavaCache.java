package org.myddd.commons.cache.guava;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import org.checkerframework.checker.guieffect.qual.PolyUI;
import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.ValueOperation;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GuavaCache<T> implements Cache<T> {

    @Override
    public ValueOperation<T> valueOperation() {
        return guavaValueOperation;
    }

    private class GuavaValueOperation implements ValueOperation<T> {

        private com.google.common.cache.Cache<String, T> cache;

        GuavaValueOperation(long duration,long maximumSize) {
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

    private GuavaValueOperation guavaValueOperation;

    public GuavaCache(long duration,long maximumSize){
        this.guavaValueOperation = new GuavaValueOperation(duration,maximumSize);
    }

}

