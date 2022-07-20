package org.myddd.extensions.security.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
class RoleRepositoryTest extends AbstractTest {

    @Inject
    private RoleRepository roleRepository;

    @Test
    void testRoleRepositoryNotNull(){
        Assertions.assertNotNull(roleRepository);
    }

    @Test
    void testQueryByUserId(){
        var role = roleRepository.save(randomRole());
        Assertions.assertNotNull(role);

        var queryRole = roleRepository.queryByRoleId(role.getRoleId());
        Assertions.assertNotNull(queryRole);
    }

    @Test
    void testAssignUserToRole(){
        var role = roleRepository.save(randomRole());
        Assertions.assertNotNull(role);

        var user = roleRepository.save(randomUser());
        Assertions.assertNotNull(user);

        boolean success = roleRepository.assignUserToRole(user,role);
        Assertions.assertTrue(success);
    }

    @Test
    void testDeAssignUserFromRole(){
        var role = roleRepository.save(randomRole());
        var user = roleRepository.save(randomUser());
        roleRepository.assignUserToRole(user,role);

        var success = roleRepository.deAssignUserFromRole(user.getId(),role.getId());
        Assertions.assertTrue(success);

        Assertions.assertDoesNotThrow(()->roleRepository.deAssignUserFromRole(randomLong(),randomLong()));
    }
}
