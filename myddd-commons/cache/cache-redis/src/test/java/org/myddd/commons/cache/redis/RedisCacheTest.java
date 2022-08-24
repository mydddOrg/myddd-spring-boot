package org.myddd.commons.cache.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.cache.AbstractTest;
import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.CacheBuilder;


public class RedisCacheTest extends AbstractTest {

    private static final Cache<String> cache = CacheBuilder.newBuilder().build(String.class);

    @Test
    void putAndGet(){
        var key = randomId();
        Assertions.assertNull(cache.valueOperation().get(key));

        cache.valueOperation().put(key,randomId());
        Assertions.assertNotNull(cache);
    }

    @Test
    void exists(){
        var key = randomId();
        Assertions.assertFalse(cache.valueOperation().exists(key));

        cache.valueOperation().put(key,randomId());
        Assertions.assertTrue(cache.valueOperation().exists(key));
    }

    @Test
    void remove(){
        var key = randomId();
        cache.valueOperation().put(key,randomId());
        Assertions.assertNotNull(cache);

        cache.valueOperation().remove(key);
        Assertions.assertNull(cache.valueOperation().get(key));
    }

    @Test
    void clearAll(){
        var key = randomId();
        cache.valueOperation().put(key,randomId());
        Assertions.assertNotNull(cache);

        cache.valueOperation().clearAll();
        Assertions.assertNull(cache.valueOperation().get(key));
    }

}
