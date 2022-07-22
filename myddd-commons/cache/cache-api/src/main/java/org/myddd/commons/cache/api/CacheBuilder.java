package org.myddd.commons.cache.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class CacheBuilder {

    private static final String GUAVA_CACHE_CLASS = "org.myddd.commons.cache.guava.GuavaCache";

    private long maximumSize = 1000;

    private long expires = 10;

    private CacheBuilder(){}

    public static CacheBuilder newBuilder(){
        return new CacheBuilder();
    }

    public CacheBuilder maximumSize(int maximumSize){
        this.maximumSize = maximumSize;
        return this;
    }

    public CacheBuilder expires(long expires) {
        this.expires = expires;
        return this;
    }

    public <T> Cache<T> build(Class<T> tClass){
        assert Objects.nonNull(tClass);

        Class<Cache<T>> cacheClass = (Class<Cache<T>>) queryCacheExists();
        try {
            Constructor<Cache<T>> ct = cacheClass.getConstructor(Long.TYPE,Long.TYPE);
            return ct.newInstance(maximumSize,expires);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new CacheNotExistsException();
        }
    }

    private Class<?> queryCacheExists(){
        Class<?> cacheClass = queryGuavaCache();

        if(Objects.isNull(cacheClass))throw new CacheNotExistsException();
        return cacheClass;
    }

    private Class<?> queryGuavaCache(){
        try {
            return Class.forName(GUAVA_CACHE_CLASS);
        }catch (ClassNotFoundException e){
            return  null;
        }
    }
}
