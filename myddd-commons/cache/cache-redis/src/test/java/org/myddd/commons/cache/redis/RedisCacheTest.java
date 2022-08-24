package org.myddd.commons.cache.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.myddd.commons.cache.AbstractTest;
import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.CacheBuilder;


@Disabled("需要启动一个REDIS服务")
public class RedisCacheTest extends AbstractTest {

    private static final Cache<String> cache = CacheBuilder.newBuilder().build(String.class);

    @Test
    void putAndGet(){
        var key = randomId();
        Assertions.assertNull(cache.opsForValue().get(key));

        cache.opsForValue().put(key,randomId());
        Assertions.assertNotNull(cache);
    }

    @Test
    void exists(){
        var key = randomId();
        Assertions.assertFalse(cache.opsForValue().exists(key));

        cache.opsForValue().put(key,randomId());
        Assertions.assertTrue(cache.opsForValue().exists(key));
    }

    @Test
    void remove(){
        var key = randomId();
        cache.opsForValue().put(key,randomId());
        Assertions.assertNotNull(cache);

        cache.opsForValue().remove(key);
        Assertions.assertNull(cache.opsForValue().get(key));
    }

    @Test
    void clearAll(){
        var key = randomId();
        cache.opsForValue().put(key,randomId());
        Assertions.assertNotNull(cache);

        cache.opsForValue().clearAll();
        Assertions.assertNull(cache.opsForValue().get(key));
    }

}
