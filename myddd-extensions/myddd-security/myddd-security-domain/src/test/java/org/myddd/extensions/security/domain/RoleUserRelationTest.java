package org.myddd.extensions.security.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;
import org.myddd.extensions.security.RoleNotFoundException;
import org.myddd.extensions.security.UserNotFoundException;

import javax.transaction.Transactional;

@Transactional
class RoleUserRelationTest extends AbstractTest {

    @Test
    void testEquals(){
        var user = randomUser().createLocalUser();
        var role = randomRole().createRole();

        var roleUserRelation = new RoleUserRelation(user,role);
        Assertions.assertTrue(roleUserRelation.getCreated() > 0);

        var anotherRoleUserRelation = new RoleUserRelation(user,role);
        Assertions.assertEquals(roleUserRelation,anotherRoleUserRelation);
    }

    @Test
    void testAssignUserToRole(){
        Assertions.assertThrows(UserNotFoundException.class,()-> RoleUserRelation.assignUserToRole(-1L,-1L));

        User createUser = randomUser().createLocalUser();
        Long userId = createUser.getId();
        Assertions.assertThrows(RoleNotFoundException.class,() -> RoleUserRelation.assignUserToRole(userId,-1L));

        Role createRole = randomRole().createRole();
        boolean success = RoleUserRelation.assignUserToRole(createUser.getId(),createRole.getId());
        Assertions.assertTrue(success);
    }

    @Test
    void testDeAssignUserFromRole(){
        Assertions.assertThrows(UserNotFoundException.class,()-> RoleUserRelation.deAssignUserFromRole(-1L,-1L));

        User createUser = randomUser().createLocalUser();
        Long userId = createUser.getId();
        Assertions.assertThrows(RoleNotFoundException.class,() -> RoleUserRelation.deAssignUserFromRole(userId,-1L));

        Role createRole = randomRole().createRole();

        boolean success = RoleUserRelation.deAssignUserFromRole(createUser.getId(),createRole.getId());
        Assertions.assertTrue(success);

        RoleUserRelation.assignUserToRole(createUser.getId(),createRole.getId());
        success = RoleUserRelation.deAssignUserFromRole(createUser.getId(),createRole.getId());
        Assertions.assertTrue(success);
    }
}
