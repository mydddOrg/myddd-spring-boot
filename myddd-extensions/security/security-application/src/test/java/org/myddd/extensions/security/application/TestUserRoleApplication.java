package org.myddd.extensions.security.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;
import org.myddd.extensions.security.RoleIdEmptyException;
import org.myddd.extensions.security.RoleNameEmptyException;
import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.api.UserRoleApplication;

import javax.inject.Inject;

class TestUserRoleApplication extends AbstractTest {

    @Inject
    private UserRoleApplication userRoleApplication;

    @Test
    void testRoleApplicationNotNull(){
        Assertions.assertNotNull(userRoleApplication);
    }

    @Test
    void testCreateRole(){
        RoleDto errorRoleDto = RoleDto.newBuilder().build();
        Assertions.assertThrows(RoleIdEmptyException.class,() -> userRoleApplication.createRole(errorRoleDto));

        RoleDto anotherErrorRoleDto = RoleDto.newBuilder()
                .setRoleId(randomId())
                .build();
        Assertions.assertThrows(RoleNameEmptyException.class,() -> userRoleApplication.createRole(anotherErrorRoleDto));

        RoleDto createdRoleDto = userRoleApplication.createRole(randomRoleDto());
        Assertions.assertNotNull(createdRoleDto);
        Assertions.assertTrue(createdRoleDto.getId() > 0);
        Assertions.assertNotNull(createdRoleDto.getRoleId());
        Assertions.assertNotNull(createdRoleDto.getName());
    }
}
