package org.myddd.security.account.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.security.account.AbstractTest;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public class TestLoginEntity extends AbstractTest {

    @Test
    public void testCreateUser(){
        LoginEntity created = createLoginUser().createUser();
        Assertions.assertTrue(created.getId() > 0);
        Assertions.assertTrue(!created.isSuperUser());
    }

    @Test
    public void testCreateSuperUser(){
        LoginEntity created = createLoginUser().createSuperUser();
        Assertions.assertTrue(created.getId() > 0);
        Assertions.assertTrue(created.isSuperUser());
    }

    @Test
    public void testIsEmpty(){
        createLoginUser().createUser();
        Assertions.assertTrue(!LoginEntity.isEmpty());
    }

    @Test
    public void testIsExists(){
        LoginEntity created = createLoginUser().createSuperUser();
        Assertions.assertTrue(LoginEntity.exists(created.getUsername()));
        Assertions.assertTrue(!LoginEntity.exists(UUID.randomUUID().toString()));
    }

    @Test
    public void testDeleteUser(){
        LoginEntity created = createLoginUser().createUser();
        LoginEntity.delete(created.getUsername());
        Assertions.assertNull(LoginEntity.findByUsername(created.getUsername()));

        LoginEntity superLogin = createLoginUser().createSuperUser();
        Assertions.assertThrows(RuntimeException.class,()->{
            LoginEntity.delete(superLogin.getUsername());
        });
    }

    @Test
    public void testEnableAndDisable(){
        LoginEntity created = createLoginUser().createSuperUser();

        Assertions.assertDoesNotThrow(() -> {
            created.enabled();
            created.disabled();
        });
    }

    @Test
    public void testFindByUsername(){
        LoginEntity created = createLoginUser().createSuperUser();
        Assertions.assertNotNull(LoginEntity.findByUsername(created.getUsername()));
    }

    @Test
    public void testUpdatePassword(){
        LoginEntity created = createLoginUser().createSuperUser();

        String newPassword = UUID.randomUUID().toString();
        Assertions.assertThrows(RuntimeException.class,()->{
            LoginEntity.updatePassword(created.getUsername(),UUID.randomUUID().toString(),newPassword);
        });

        Assertions.assertDoesNotThrow(() -> {
            LoginEntity.updatePassword(created.getUsername(),created.getPassword(),newPassword);
        });

        LoginEntity findLoginEntity = LoginEntity.findByUsername(created.getUsername());
        Assertions.assertTrue(findLoginEntity.getPassword().equals(newPassword));
    }

    @Test
    public void testUpdatePasswordByAdmin(){
        LoginEntity created = createLoginUser().createSuperUser();
        String newPassword = UUID.randomUUID().toString();
        Assertions.assertDoesNotThrow(() -> {
            LoginEntity.updatePasswordByAdmin(created.getUsername(),newPassword);
        });
    }

    @Test
    public void testUpdateUser(){
        LoginEntity created = createLoginUser().createUser();

        String newDisplayName = UUID.randomUUID().toString();
        created.setDisplayName(newDisplayName);

        LoginEntity updated =  created.updateUser();
        Assertions.assertTrue(updated.getDisplayName().equals(newDisplayName));
    }

    private LoginEntity createLoginUser() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUsername(UUID.randomUUID().toString());
        loginEntity.setPassword(UUID.randomUUID().toString());
        loginEntity.setDisplayName(UUID.randomUUID().toString());

        return loginEntity;
    }
}
