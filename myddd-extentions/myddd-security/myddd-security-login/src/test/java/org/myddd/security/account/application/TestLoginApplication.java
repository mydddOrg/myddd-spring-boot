package org.myddd.security.account.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.security.account.AbstractTest;
import org.myddd.security.api.LoginApplication;
import org.myddd.security.api.LoginDTO;
import org.myddd.utils.Page;

import javax.inject.Inject;
import java.util.UUID;

public class TestLoginApplication extends AbstractTest {

    @Inject
    private LoginApplication loginApplication;

    private static final String password = UUID.randomUUID().toString();

    @Test
    public void testCreateSuper(){
        LoginDTO created = loginApplication.createSupper(createLoginDTO());
        Assertions.assertTrue(created.getId() > 0);
        Assertions.assertTrue(created.isSuperUser());


        Assertions.assertThrows(RuntimeException.class,()->{
            deleteUser(created.getUsername());
        });
    }

    @Test
    public void testCreateUser(){
        LoginDTO created = loginApplication.createUser(createLoginDTO());
        Assertions.assertTrue(created.getId() > 0);
        Assertions.assertTrue(!created.isSuperUser());

        deleteUser(created.getUsername());

    }

    @Test
    public void testQueryLogin(){
        LoginDTO created = loginApplication.createUser(createLoginDTO());

        LoginDTO queryLogin = loginApplication.queryLogin(created.getUsername());
        Assertions.assertNotNull(queryLogin);

        LoginDTO anotherQueryLogin = loginApplication.queryLogin(UUID.randomUUID().toString());
        Assertions.assertNull(anotherQueryLogin);

        deleteUser(created.getUsername());
    }

    @Test
    public void testIsEmpty(){
        loginApplication.createUser(createLoginDTO());
        Assertions.assertFalse(loginApplication.isEmpty());
    }

    @Test
    public void testUpdatePassword(){
        LoginDTO created = loginApplication.createUser(createLoginDTO());
        String newPassword = UUID.randomUUID().toString();

        Assertions.assertThrows(RuntimeException.class,()->{
            created.setPassword(UUID.randomUUID().toString());
            created.setNewPassword(UUID.randomUUID().toString());
            loginApplication.updatePassword(created);
        });

        Assertions.assertDoesNotThrow(() -> {
            created.setPassword(password);
            created.setNewPassword(newPassword);
            loginApplication.updatePassword(created);
        });

    }

    @Test
    public void testUpdatePasswordByAdmin(){
        LoginDTO created = loginApplication.createUser(createLoginDTO());
        String newPassword = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> {
            created.setNewPassword(newPassword);
            loginApplication.updatePasswordByAdmin(created);
        });
    }

    @Test
    public void testPageQueryUser(){
        LoginDTO created = loginApplication.createUser(createLoginDTO());

        Page<LoginDTO> page = loginApplication.pageListLogin(0,100, "");
        Assertions.assertTrue(page.getResultCount() > 0);

        page = loginApplication.pageListLogin(0,100, UUID.randomUUID().toString());
        Assertions.assertTrue(page.getResultCount() == 0);

        page = loginApplication.pageListLogin(0,100, created.getUsername());
        Assertions.assertTrue(page.getResultCount() > 0);
    }

    @Test
    public void testUpdateUser(){
        LoginDTO created = loginApplication.createUser(createLoginDTO());

        String newDisplayName = UUID.randomUUID().toString();

        created.setDisplayName(newDisplayName);

        loginApplication.updateUser(created);
    }

    @Test
    public void testEnableUser(){
        Assertions.assertThrows(RuntimeException.class,() -> {
            loginApplication.enableUser(UUID.randomUUID().toString());
        });
        LoginDTO created = loginApplication.createUser(createLoginDTO());

        Assertions.assertDoesNotThrow(()->{
            loginApplication.enableUser(created.getUsername());
        });
    }

    @Test
    public void testDisableUser(){
        Assertions.assertThrows(RuntimeException.class,() -> {
            loginApplication.enableUser(UUID.randomUUID().toString());
        });
        LoginDTO created = loginApplication.createUser(createLoginDTO());

        Assertions.assertDoesNotThrow(()->{
            loginApplication.disableUser(created.getUsername());
        });
    }


    private void deleteUser(String username){
        loginApplication.deleteUser(username);
    }

    private LoginDTO createLoginDTO(){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(UUID.randomUUID().toString());
        loginDTO.setPassword(password);
        loginDTO.setDisplayName(UUID.randomUUID().toString());
        return loginDTO;
    }


}
