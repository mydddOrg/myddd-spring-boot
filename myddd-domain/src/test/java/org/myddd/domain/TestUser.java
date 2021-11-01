package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.*;

import javax.transaction.Transactional;

@Transactional
class TestUser extends AbstractTest {

    @Test
    void testCreateUser(){
        User user = new User();
        Assertions.assertThrows(UserNameEmptyException.class,user::createLocalUser);


        User noPasswordUser = new User();
        noPasswordUser.setUserId(randomId());
        noPasswordUser.setName(randomId());

        Assertions.assertThrows(PasswordEmptyException.class, noPasswordUser::createLocalUser);

        User noUserIdUser = new User();
        noUserIdUser.setName(randomId());
        noUserIdUser.setPassword(randomId());
        Assertions.assertThrows(UserIdEmptyException.class, noUserIdUser::createLocalUser);

        User randomUser = randomUser();
        Assertions.assertTrue(randomUser.notExisted());

        User createdUser = randomUser.createLocalUser();
        Assertions.assertNotNull(createdUser);
        Assertions.assertTrue(createdUser.existed());
        Assertions.assertTrue(createdUser.getCreated() > 0);
        Assertions.assertTrue(createdUser.getUpdated() > 0);

        Assertions.assertThrows(IllegalArgumentException.class,()->createdUser.setId(-1L));
    }


    @Test
    void testQueryUserByUserId(){

        User notExists = User.queryUserByUserId(randomId());
        Assertions.assertNull(notExists);

        User createdUser = randomUser().createLocalUser();
        Assertions.assertNotNull(createdUser);

        User query = User.queryUserByUserId(createdUser.getUserId());
        Assertions.assertNotNull(query);
    }

    @Test
    void testEnableUser(){
        User randomUser = randomUser();
        Assertions.assertThrows(UserNotFoundException.class, randomUser::enable);

        User createdUser = randomUser().createLocalUser();
        createdUser.disable();
        Assertions.assertTrue(createdUser.isDisabled());

        createdUser.enable();
        Assertions.assertFalse(createdUser.isDisabled());
    }

}
