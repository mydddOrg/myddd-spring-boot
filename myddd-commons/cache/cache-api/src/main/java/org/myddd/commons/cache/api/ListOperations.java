package org.myddd.commons.cache.api;


import java.util.Collection;
import java.util.List;

public interface ListOperations<V> {

    List<V> range(String key, long start, long end);

    void trim(String key, long start, long end);

    Long size(String key);

    Long leftPush(String key, V value);

    Long leftPushAll(String key, V... values);

    Long leftPushAll(String key, Collection<V> values);

    Long leftPushIfPresent(String key, V value);

    Long leftPush(String key, V pivot, V value);

    Long rightPush(String key, V value);

    Long rightPushAll(String key, V... values);

    Long rightPushAll(String key, Collection<V> values);

    Long rightPushIfPresent(String key, V value);

    Long rightPush(String key, V pivot, V value);
}
