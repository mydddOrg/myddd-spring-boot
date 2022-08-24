package org.myddd.commons.cache.api;

public interface ValueOperation<T> {

    void put(String key,T value);

    T get(String key);

    boolean exists(String key);

    void remove(String key);

    void clearAll();

}
