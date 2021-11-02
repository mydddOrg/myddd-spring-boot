package org.myddd.ioc.spring;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.IocInstanceNotFoundException;
import org.myddd.domain.IocInstanceNotUniqueException;
import org.myddd.ioc.spring.mock.DefaultPasswordEncoder;
import org.myddd.ioc.spring.mock.Fly;
import org.myddd.ioc.spring.mock.NoImplementInterface;
import org.myddd.ioc.spring.mock.PasswordEncoder;

import java.util.Set;

class TestInstanceProvider extends AbstractTest{

    @Test
    void getGetInstance(){
        PasswordEncoder passwordEncoder = instanceProvider.getInstance(PasswordEncoder.class);
        Assertions.assertNotNull(passwordEncoder);

        PasswordEncoder theSomePasswordEncoder = instanceProvider.getInstance(DefaultPasswordEncoder.class);
        Assertions.assertNotNull(theSomePasswordEncoder);

        NoImplementInterface notExists = instanceProvider.getInstance(NoImplementInterface.class);
        Assertions.assertNull(notExists);

        Assertions.assertThrows(IocInstanceNotUniqueException.class,()-> instanceProvider.getInstance(Fly.class));
    }

    @Test
    void testGetInstanceWithName(){
        Fly birdFly = instanceProvider.getInstance(Fly.class,"bird");
        Assertions.assertNotNull(birdFly);

        Fly notExists = instanceProvider.getInstance(Fly.class, "NO_EXISTS");
        Assertions.assertNull(notExists);
    }

    @Test
    void testGetInstances(){
        Set<Fly> flies =  instanceProvider.getInstances(Fly.class);
        Assertions.assertEquals(2,flies.size());
    }
}
