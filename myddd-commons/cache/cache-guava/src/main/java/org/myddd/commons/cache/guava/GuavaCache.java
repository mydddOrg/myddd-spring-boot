package org.myddd.commons.cache.guava;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.ListOperations;
import org.myddd.commons.cache.api.ValueOperations;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GuavaCache<T> implements Cache<T> {

    @Override
    public ValueOperations<T> opsForValue() {
        return guavaValueOperation;
    }

    @Override
    public ListOperations<T> optForList() {
        return null;
    }

    private GuavaValueOperations<T> guavaValueOperation;

    public GuavaCache(long duration,long maximumSize){
        this.guavaValueOperation = new GuavaValueOperations<>(duration,maximumSize);
    }

}

