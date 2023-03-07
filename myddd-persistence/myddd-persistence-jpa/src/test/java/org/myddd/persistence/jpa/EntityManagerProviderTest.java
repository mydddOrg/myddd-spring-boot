package org.myddd.persistence.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.persistence.AbstractTest;

import jakarta.inject.Inject;


class EntityManagerProviderTest extends AbstractTest {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    @Test
    void testProviderNotNull(){
        Assertions.assertNotNull(entityManagerProvider);
    }

    @Test
    void testGetEntityManagerFromIoC(){
        Assertions.assertNotNull(entityManagerProvider.getEntityManagerFromIoC());
    }

    @Test
    void testGetEntityManager(){
        Assertions.assertNotNull(entityManagerProvider.getEntityManager());
    }

    @Test
    void testGetEntityManagerFactory(){
        Assertions.assertNotNull(entityManagerProvider.getEntityManagerFactory());
    }
}
