package org.myddd.extensions.security.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

class UserRepositoryTest extends AbstractTest {

    @Inject
    private UserRepository userRepository;

    @Test
    void testUserRepositoryNotNull(){
        Assertions.assertNotNull(userRepository);
    }

    @Test
    @Transactional
    void testQueryUserById(){
        User notExistUser = userRepository.queryLocalUserByUserId(randomId());
        Assertions.assertNull(notExistUser);

        User created = userRepository.save(randomUser());
        Assertions.assertNotNull(created);

        User query = userRepository.queryLocalUserByUserId(created.getUserId());
        Assertions.assertNotNull(query);
    }

    @Test
    @Transactional
    void testQueryUserByEmail(){
        Assertions.assertNull(userRepository.queryUserByEmail(randomId(),UserType.LOCAL));

        User created = userRepository.save(randomUser());
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(userRepository.queryUserByEmail(created.getEmail(),UserType.LOCAL));
    }

    @Test
    @Transactional
    void testQueryUserByPhone(){
        Assertions.assertNull(userRepository.queryUserByPhone(randomId(),UserType.LOCAL));

        var user = randomUser();
        user.setPhone("18620501006");
        var createdUser = user.createLocalUser();

        Assertions.assertNotNull(userRepository.queryUserByPhone(createdUser.getPhone(),UserType.LOCAL));
    }

    @Test
    @Transactional
    void testBatchInsertUsers(){
        var list = List.of(randomUser(),randomUser());
        userRepository.batchSaveEntities(list);

        var userIdList = userRepository.queryExistsUserIdByType(UserType.LOCAL);
        Assertions.assertFalse(userIdList.isEmpty());
    }
}
