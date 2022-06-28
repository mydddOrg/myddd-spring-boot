package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.User;

import javax.transaction.Transactional;

@Transactional
class BaseDistributedEntityTest extends AbstractTest {

    private Long savedId;

    @BeforeEach
    @Transactional
    public void beforeEach(){
        savedId = randomUser().createUser().getId();
        Assertions.assertTrue(savedId > 0);

        User queryUser = User.queryByUserId(savedId);
        Assertions.assertNotNull(queryUser);
    }

    @Test
    void testQueryUser(){
        User queryUser = User.queryByUserId(savedId);
        Assertions.assertNotNull(queryUser);
    }

    @Test
    void testExists(){
        User randomUser = randomUser();
        Assertions.assertFalse(randomUser.existed());

        User created = randomUser.createUser();
        Assertions.assertTrue(created.existed());
    }

    @Test
    void testNotExists(){
        User randomUser = randomUser();
        Assertions.assertTrue(randomUser.notExisted());

        User created = randomUser.createUser();
        Assertions.assertFalse(created.notExisted());
    }

    @Test
    void testSetId(){
        User randomUser = randomUser();
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            randomUser.setId(-1L);
        });

        randomUser.setId(100L);
        Assertions.assertEquals(100L,randomUser.getId());
    }

    @Test
    void testVersionProperty(){
        User randomUser = randomUser();
        Assertions.assertEquals(0,randomUser.getVersion());
    }
}
