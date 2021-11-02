package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.domain.mock.MD5PasswordEncode;
import org.myddd.domain.mock.PasswordEncode;

import java.util.UUID;

class TestInstanceFactory {


    private final static InstanceProvider instanceProvider = Mockito.mock(InstanceProvider.class);


    @BeforeAll
    static void beforeAll(){
        InstanceFactory.setInstanceProvider(instanceProvider);
    }

    @Test
    void testGetInstance(){
        Mockito.when(instanceProvider.getInstance(PasswordEncode.class)).thenReturn(new MD5PasswordEncode());

        PasswordEncode passwordEncode = instanceProvider.getInstance(PasswordEncode.class);
        Assertions.assertNotNull(passwordEncode);

        Mockito.when(instanceProvider.getInstance(PasswordEncode.class)).thenReturn(null);
        Assertions.assertThrows(IocInstanceNotFoundException.class,() -> InstanceFactory.getInstance(PasswordEncode.class));
    }

    @Test
    void testGetInstanceWithDefault(){
        PasswordEncode defaultPasswordEncode = new MD5PasswordEncode();
        PasswordEncode mockedPasswordEncode = new MD5PasswordEncode();

        Mockito.when(instanceProvider.getInstance(PasswordEncode.class)).thenReturn(mockedPasswordEncode);
        PasswordEncode passwordEncode = InstanceFactory.getInstanceWithDefault(PasswordEncode.class,defaultPasswordEncode);
        Assertions.assertEquals(mockedPasswordEncode,passwordEncode);

        Mockito.when(instanceProvider.getInstance(PasswordEncode.class)).thenReturn(null);
        PasswordEncode queryInstanceFactory = InstanceFactory.getInstanceWithDefault(PasswordEncode.class,defaultPasswordEncode);
        Assertions.assertEquals(queryInstanceFactory,defaultPasswordEncode);
    }


    @Test
    void testGetInstanceWithName(){
        Mockito.when(instanceProvider.getInstance(Mockito.any(),Mockito.anyString())).thenReturn(new MD5PasswordEncode());
        PasswordEncode passwordEncode = InstanceFactory.getInstance(PasswordEncode.class, UUID.randomUUID().toString());
        Assertions.assertNotNull(passwordEncode);

        Mockito.when(instanceProvider.getInstance(Mockito.any(),Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(IocInstanceNotFoundException.class,()->InstanceFactory.getInstance(PasswordEncode.class,"NOT_EXISTS"));
    }

}
