package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.OrgRoleNotExistsException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
class OrgRoleTest extends AbstractTest {

    @Test
    void testCreateRole(){

        OrgRole companyNotExistsOrgRole = randomOrgRole();
        companyNotExistsOrgRole.setCompany(randomCompany());
        Assertions.assertThrows(OrganizationNotExistsException.class,companyNotExistsOrgRole::createRole);

        OrgRole createdRole = createOrgRole();
        Assertions.assertNotNull(createdRole);
    }

    @Test
    void testRemoveRole(){
        var notExistOrgRoleId = randomLong();
        Assertions.assertThrows(OrgRoleNotExistsException.class,() -> OrgRole.removeRole(notExistOrgRoleId));

        var createdRole = createOrgRole();
        Assertions.assertDoesNotThrow(()->OrgRole.removeRole(createdRole.getId()));

        var notEmptyRole = createOrgRole();
        var createdEmployee = randomEmployee(notEmptyRole.getCompany().getId()).createEmployee();
        EmployeeRoleAssignment.assignEmployeeToRole(createdEmployee.getId(),notEmptyRole.getId());

        var orgRoleId = notEmptyRole.getId();
        Assertions.assertThrows(OrgRoleNotEmptyException.class,()->OrgRole.removeRole(orgRoleId));
    }

    @Test
    void testUpdateRole(){
        var notExistOrgRole = randomOrgRole();
        Assertions.assertThrows(OrgRoleNotExistsException.class, notExistOrgRole::updateRole);

        OrgRole createdRole = createOrgRole();
        String newName = randomId();
        createdRole.setName(newName);
        OrgRole updated = createdRole.updateRole();
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(newName,updated.getName());
    }
    @Test
    void testQueryRolesByCompany(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);
        List<OrgRole> emptyRoles = OrgRole.queryRolesByCompany(created.getId());
        Assertions.assertTrue(emptyRoles.isEmpty());


        var orgRoleGroup = randomOrgRoleGroup(created).createRoleGroup();

        OrgRole orgRole = randomOrgRole();
        orgRole.setCompany(created);
        orgRole.setOrgRoleGroup(orgRoleGroup);

        OrgRole createdRole = orgRole.createRole();
        Assertions.assertNotNull(createdRole);

        List<OrgRole> roles = OrgRole.queryRolesByCompany(created.getId());
        Assertions.assertFalse(roles.isEmpty());
    }

    @Test
    void testQueryById(){
        Assertions.assertNull(OrgRole.queryById(-1L));

        OrgRole createdRole = createOrgRole();
        Assertions.assertNotNull(createdRole);
        Assertions.assertNotNull(OrgRole.queryById(createdRole.getId()));
    }

    @Test
    void testChangeOrgRoleGroup(){
        OrgRole createdRole = createOrgRole();
        var orgRoleGroup = randomOrgRoleGroup(createdRole.getCompany()).createRoleGroup();
        Assertions.assertDoesNotThrow(()-> OrgRole.changeOrgRoleGroup(createdRole.getId(),orgRoleGroup.getId()));
    }

    private OrgRole createOrgRole(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);

        OrgRole orgRole = randomOrgRole();

        var orgRoleGroup = randomOrgRoleGroup(created).createRoleGroup();
        Assertions.assertNotNull(orgRoleGroup);

        orgRole.setCompany(created);
        orgRole.setOrgRoleGroup(orgRoleGroup);

        return orgRole.createRole();
    }
}
