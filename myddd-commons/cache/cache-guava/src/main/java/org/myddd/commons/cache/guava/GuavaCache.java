package org.myddd.commons.cache.guava;

import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.ValueOperations;

public class GuavaCache<T> implements Cache<T> {

    @Override
    public ValueOperations<T> opsForValue() {
        return guavaValueOperation;
    }

    private GuavaValueOperations<T> guavaValueOperation;

    public GuavaCache(long duration,long maximumSize){
        this.guavaValueOperation = new GuavaValueOperations<>(duration,maximumSize);
    }

}

