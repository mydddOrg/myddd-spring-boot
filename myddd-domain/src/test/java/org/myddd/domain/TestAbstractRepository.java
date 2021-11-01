package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.User;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
class TestAbstractRepository extends AbstractTest{

    @Inject
    private AbstractRepository repository;

    @Test
    void testRepositoryNotNull(){
        Assertions.assertNotNull(repository);
    }

    @Test
    void testSaveEntity(){
        User user = randomUser();

        User created = repository.save(user);
        Assertions.assertNotNull(created);
    }

    @Test
    void testGetEntity(){
        User notExistsUser = repository.get(User.class,1L);
        Assertions.assertNull(notExistsUser);

        User created = repository.save(randomUser());
        User queryUser = repository.get(User.class,created.getId());
        Assertions.assertNotNull(queryUser);
    }

    @Test
    void testRemoveEntity(){
        User created = repository.save(randomUser());
        User queryUser = repository.get(User.class,created.getId());
        Assertions.assertNotNull(queryUser);

        repository.remove(created);
        queryUser = repository.get(User.class,created.getId());
        Assertions.assertNull(queryUser);
    }

    @Test
    void testEntityExists(){
        boolean exists = repository.exists(User.class,-1L);
        Assertions.assertFalse(exists);


        User created = repository.save(randomUser());
        exists = repository.exists(User.class,created.getId());
        Assertions.assertTrue(exists);
    }
}
