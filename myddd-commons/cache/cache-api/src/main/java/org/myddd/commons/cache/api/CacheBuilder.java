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
        Class<Cache<T>> cacheClass = (Class<Cache<T>>) queryCacheExists();
        if(Objects.isNull(cacheClass))throw new CacheImplementNotExistsException();

        try {
            Constructor ct
                    = cacheClass.getConstructor(new Class[]{Long.TYPE,Long.TYPE});
            return (Cache<T>) ct.newInstance(maximumSize,expires);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new CacheImplementNotExistsException();
        }
    }

    private Class<?> queryCacheExists(){
        Class<?> cacheClass = null;
        try {cacheClass = Class.forName(GUAVA_CACHE_CLASS);}catch (ClassNotFoundException ignored){}
        return cacheClass;
    }


}
