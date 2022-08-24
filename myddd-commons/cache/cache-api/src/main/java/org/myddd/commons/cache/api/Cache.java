package org.myddd.commons.cache.api;

public interface Cache<T> {
    ValueOperation<T> valueOperation();
}
