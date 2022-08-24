package org.myddd.commons.cache.api;

public interface Cache<T> {
    ValueOperations<T> opsForValue();
}
