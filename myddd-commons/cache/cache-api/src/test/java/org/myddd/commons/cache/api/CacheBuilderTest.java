package org.myddd.commons.cache.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheBuilderTest {

    @Test
    void buildCache(){
        Cache<String> cache = CacheBuilder.newBuilder().build(String.class);
        Assertions.assertNotNull(cache);

        var anotherCache = CacheBuilder.newBuilder().expires(10).maximumSize(1000).build(String.class);
        Assertions.assertNotNull(anotherCache);
    }
}
