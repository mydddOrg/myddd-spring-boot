package org.myddd.persistence.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.persistence.AbstractTest;
import org.myddd.persistence.mock.User;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Transactional
class TestEntityRepository extends AbstractTest {

    @Inject
    private EntityRepositoryJpa entityRepositoryJpa;

    @Test
    void testEntityRepositoryJpaNotNull(){
        Assertions.assertNotNull(entityRepositoryJpa);
    }


    @Test
    void testLoad(){
        User created = randomUser().createLocalUser();
        User query = entityRepositoryJpa.load(User.class,created.getId());
        Assertions.assertNotNull(query);

        User notExists = entityRepositoryJpa.load(User.class,-1L);
        Assertions.assertThrows(EntityNotFoundException.class, notExists::toString);
    }

    @Test
    void testCreate(){
        User created = entityRepositoryJpa.save(randomUser());
        Assertions.assertNotNull(created);

        User user = new User();
        Assertions.assertThrows(PersistenceException.class,()-> entityRepositoryJpa.save(user));
    }

    @Test
    void testUpdate(){
        User created = entityRepositoryJpa.save(randomUser());
        Assertions.assertNotNull(created);

        created.setUserId(randomId());
        User updated = entityRepositoryJpa.save(created);
        Assertions.assertNotNull(updated);
    }

    @Test
    void testGet(){
        User created = entityRepositoryJpa.save(randomUser());
        Assertions.assertNotNull(created);

        User query = entityRepositoryJpa.get(User.class,created.getId());
        Assertions.assertNotNull(query);

        User notExists = entityRepositoryJpa.get(User.class,-1L);
        Assertions.assertNull(notExists);
    }

    @Test
    void testExists(){
        User randomUser = new User();
        boolean exists = entityRepositoryJpa.exists(User.class,randomUser.getId());
        Assertions.assertFalse(exists);

        User created = entityRepositoryJpa.save(randomUser());
        Assertions.assertNotNull(created);
        exists = entityRepositoryJpa.exists(User.class,created.getId());
        Assertions.assertTrue(exists);
    }

    @Test
    void testRemove(){
        User created = entityRepositoryJpa.save(randomUser());
        Assertions.assertNotNull(created);

        boolean exists = entityRepositoryJpa.exists(User.class,created.getId());
        Assertions.assertTrue(exists);

        entityRepositoryJpa.remove(created);
        exists = entityRepositoryJpa.exists(User.class,created.getId());
        Assertions.assertFalse(exists);

    }
}
