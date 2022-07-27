package org.myddd.extensions.security.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;
import org.myddd.extensions.security.RoleIdEmptyException;
import org.myddd.extensions.security.RoleNameEmptyException;
import org.myddd.extensions.security.RoleNotFoundException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Transactional
class RoleTest extends AbstractTest {

    @Inject
    private RoleRepository repository;

    @Test
    void testEquals(){
        var role = randomRole();
        var anotherRole = new Role();
        anotherRole.setRoleId(role.getRoleId());

        Assertions.assertEquals(role,anotherRole);
    }

    @Test
    void testCreateRole(){
        Role errorRole = new Role();
        Assertions.assertThrows(RoleIdEmptyException.class, errorRole::createRole);

        errorRole.setRoleId(randomId());
        Assertions.assertThrows(RoleNameEmptyException.class, errorRole::createRole);

        Role created = randomRole().createRole();
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getRoleId());
        Assertions.assertNotNull(created.getName());
        Assertions.assertEquals(0,created.getUpdated());
        Assertions.assertTrue(created.getCreated() > 0);
    }

    @Test
    void testQueryByRoleId(){
        Role created = randomRole().createRole();
        Assertions.assertNotNull(created);

        Role query = Role.queryByRoleId(created.getRoleId());
        Assertions.assertNotNull(query);
    }

    @Test
    void testUniqueRoleId(){
        Role created = randomRole().createRole();
        Assertions.assertNotNull(created);

        Role uniqueRoleIdRole = new Role();
        uniqueRoleIdRole.setRoleId(created.getRoleId());
        uniqueRoleIdRole.setName(randomId());

        var anotherCreated = uniqueRoleIdRole.createRole();

        Assertions.assertThrows(PersistenceException.class, () -> repository.flush());
    }

    @Test
    void testUpdateRole(){
        Role randomRole = randomRole();
        Assertions.assertThrows(RoleNotFoundException.class,randomRole::updateRole);

        Role created = randomRole.createRole();
        Assertions.assertNotNull(created);

        String newName = randomId();
        created.setName(newName);

        Role updated = created.updateRole();
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(newName,updated.getName());
    }
}
