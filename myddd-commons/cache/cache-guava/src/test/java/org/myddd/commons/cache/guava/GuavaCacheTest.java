package org.myddd.commons.cache.guava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.cache.AbstractTest;
import org.myddd.commons.cache.api.Cache;

import javax.inject.Inject;

class GuavaCacheTest extends AbstractTest {


    @Inject
    private Cache<String> cache;

    @Test
    void putAndGet(){
        var key = randomId();
        Assertions.assertNull(cache.get(key));

        cache.put(key,randomId());
        Assertions.assertNotNull(cache);
    }

    @Test
    void exists(){
        var key = randomId();
        Assertions.assertFalse(cache.exists(key));

        cache.put(key,randomId());
        Assertions.assertTrue(cache.exists(key));
    }

    @Test
    void remove(){
        var key = randomId();
        cache.put(key,randomId());
        Assertions.assertNotNull(cache);

        cache.remove(key);
        Assertions.assertNull(cache.get(key));
    }

    @Test
    void clearAll(){
        var key = randomId();
        cache.put(key,randomId());
        Assertions.assertNotNull(cache);

        cache.clearAll();
        Assertions.assertNull(cache.get(key));
    }
}
