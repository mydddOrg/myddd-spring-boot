package org.myddd.security.account.infra;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.security.account.domain.LoginEntity;
import org.myddd.security.account.domain.LoginRepository;
import org.myddd.security.account.AbstractTest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public class TestLoginRepository extends AbstractTest {

    @Inject
    private LoginRepository loginRepository;

    @Test
    public void testCreateUser() {
        LoginEntity loginEntity = createLoginUser();
        LoginEntity createdLoginEntity = loginRepository.createLoginUser(loginEntity);
        Assertions.assertTrue(createdLoginEntity.getId() > 0);
    }

    @Test
    public void testFindByUsername() {
        LoginEntity createdLoginEntity = loginRepository.createLoginUser(createLoginUser());
        LoginEntity findLoginEntity = loginRepository.findByUsername(createdLoginEntity.getUsername());
        Assertions.assertNotNull(findLoginEntity);
    }

    @Test
    public void testIsEmpty() {
        loginRepository.createLoginUser(createLoginUser());
        Assertions.assertTrue(!loginRepository.isEmpty());
    }

    @Test
    public void testUserExits() {
        Assertions.assertTrue(!loginRepository.userExists(UUID.randomUUID().toString()));
        LoginEntity createdLoginEntity = loginRepository.createLoginUser(createLoginUser());
        Assertions.assertTrue(loginRepository.userExists(createdLoginEntity.getUsername()));
    }

    @Test
    public void testDeleteUser(){
        LoginEntity createdLoginEntity = loginRepository.createLoginUser(createLoginUser());

        Assertions.assertDoesNotThrow(() -> {
            loginRepository.deleteUser(createdLoginEntity.getUsername());
        });


        LoginEntity superLoginEntity = createLoginUser();
        superLoginEntity.setSuperUser(true);
        LoginEntity createSuper = loginRepository.createLoginUser(superLoginEntity);

        Assertions.assertThrows(RuntimeException.class,() -> {
            loginRepository.deleteUser(createSuper.getUsername());
        });

    }

    @Test
    public void testEnable(){
        LoginEntity createdLoginEntity = loginRepository.createLoginUser(createLoginUser());
        Assertions.assertDoesNotThrow(()->{
            loginRepository.enable(createdLoginEntity);
        });
    }

    @Test
    public void testDisable(){
        LoginEntity createdLoginEntity = loginRepository.createLoginUser(createLoginUser());
        Assertions.assertDoesNotThrow(()->{
            loginRepository.disable(createdLoginEntity);
        });
    }

    private LoginEntity createLoginUser() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUsername(UUID.randomUUID().toString());
        loginEntity.setPassword(UUID.randomUUID().toString());
        loginEntity.setDisplayName(UUID.randomUUID().toString());

        return loginEntity;
    }

}
