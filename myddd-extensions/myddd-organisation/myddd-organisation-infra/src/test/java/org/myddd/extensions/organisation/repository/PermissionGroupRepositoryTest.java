package org.myddd.extensions.organisation.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.domain.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Set;

@Transactional
class PermissionGroupRepositoryTest extends AbstractTest {

    @Inject
    private PermissionGroupRepository repository;

    @Test
    void testRepositoryNotNull(){
        Assertions.assertNotNull(repository);
    }

    @Test
    void testDeletePermissionGroup(){
        Assertions.assertDoesNotThrow(() -> {
            repository.removePermissionGroupById(-1L);
        });

        PermissionGroup created = randomPermissionGroup().createPermissionGroup();
        Assertions.assertDoesNotThrow(() -> {
            repository.removePermissionGroupById(created.getId());
        });
    }

    @Test
    void testBatchSavePermissionGroupRole(){
        Set<PermissionGroupRole> randomPermissionGroupRole = Set.of(createPermissionGroupRole(),createPermissionGroupRole(),createPermissionGroupRole());
        Assertions.assertDoesNotThrow(()->repository.batchSavePermissionGroupRole(randomPermissionGroupRole));
    }

    @Test
    void testQueryPermissionRoles(){
        Assertions.assertTrue(repository.queryPermissionGroupRolesById(-1L).isEmpty());

        PermissionGroupRole permissionGroupRole = repository.save(createPermissionGroupRole());
        Assertions.assertFalse(repository.queryPermissionGroupRolesById(permissionGroupRole.getPermissionGroup().getId()).isEmpty());
    }

    @Test
    void testBatchQueryPermissionRoles(){
        Assertions.assertTrue(repository.batchQueryPermissionGroupRolesByIds(Set.of()).isEmpty());

        PermissionGroupRole permissionGroupRole = repository.save(createPermissionGroupRole());
        Assertions.assertFalse(repository.batchQueryPermissionGroupRolesByIds(Set.of(permissionGroupRole.getPermissionGroup().getId())).isEmpty());
    }

    @Test
    void testBatchSavePermissionGroupOrganization(){
        Set<PermissionGroupOrganization> permissionGroupOrganizations = Set.of(createPermissionGroupOrganization(),createPermissionGroupOrganization());
        Assertions.assertDoesNotThrow(()->repository.batchSavePermissionGroupOrganization(permissionGroupOrganizations));
    }

    @Test
    void testQueryPermissionGroupOrganizations(){
        Assertions.assertTrue(repository.queryPermissionGroupOrganizationsById(-1L).isEmpty());

        PermissionGroupOrganization created = repository.save(createPermissionGroupOrganization());
        Assertions.assertFalse(repository.queryPermissionGroupOrganizationsById(created.getPermissionGroup().getId()).isEmpty());
    }

    @Test
    void testBatchQueryPermissionGroupOrganizationsByIds(){
        Assertions.assertTrue(repository.batchQueryPermissionGroupOrganizationsByIds(Set.of()).isEmpty());

        PermissionGroupOrganization created = repository.save(createPermissionGroupOrganization());
        Assertions.assertFalse(repository.batchQueryPermissionGroupOrganizationsByIds(Set.of(created.getPermissionGroup().getId())).isEmpty());
    }

    @Test
    void testBatchSavePermissionGroupEmployee(){
        Set<PermissionGroupEmployee> permissionGroupEmployees = Set.of(createPermissionGroupEmployee(),createPermissionGroupEmployee(),createPermissionGroupEmployee(),createPermissionGroupEmployee());
        Assertions.assertDoesNotThrow(()->repository.batchSavePermissionGroupEmployee(permissionGroupEmployees));
    }

    @Test
    void testQueryPermissionGroupEmployee(){
        Assertions.assertTrue(repository.queryPermissionGroupEmployeesById(-1L).isEmpty());

        PermissionGroupEmployee created = repository.save(createPermissionGroupEmployee());
        Assertions.assertFalse(repository.queryPermissionGroupEmployeesById(created.getPermissionGroup().getId()).isEmpty());
    }

    @Test
    void testBatchQueryPermissionGroupEmployee(){
        Assertions.assertTrue(repository.batchQueryPermissionGroupEmployeesByIds(Set.of()).isEmpty());

        PermissionGroupEmployee created = repository.save(createPermissionGroupEmployee());
        Assertions.assertFalse(repository.batchQueryPermissionGroupEmployeesByIds(Set.of(created.getPermissionGroup().getId())).isEmpty());
    }

    @Test
    void testListPermissionGroup(){
        Assertions.assertTrue(repository.listPermissionGroupByTypeAndRelateId(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM,randomId()).isEmpty());

        PermissionGroupEmployee created = repository.save(createPermissionGroupEmployee());
        Assertions.assertFalse(repository.listPermissionGroupByTypeAndRelateId(created.getPermissionGroup().getType(),created.getPermissionGroup().getRelateId()).isEmpty());
    }

    private PermissionGroupEmployee createPermissionGroupEmployee(){
        PermissionGroupEmployee permissionGroupEmployee = new PermissionGroupEmployee();
        permissionGroupEmployee.setPermissionGroup(randomPermissionGroup().createPermissionGroup());

        permissionGroupEmployee.setEmployee(randomEmployee().createEmployee());
        return permissionGroupEmployee;
    }

    private PermissionGroupOrganization createPermissionGroupOrganization(){
        PermissionGroupOrganization permissionGroupOrganization = new PermissionGroupOrganization();
        permissionGroupOrganization.setPermissionGroup(randomPermissionGroup().createPermissionGroup());

        Organization organization = randomCompany().createTopCompany();
        permissionGroupOrganization.setOrganization(organization);

        return permissionGroupOrganization;
    }


    private PermissionGroupRole createPermissionGroupRole(){
        PermissionGroupRole permissionGroupRole = new PermissionGroupRole();
        permissionGroupRole.setPermissionGroup(randomPermissionGroup().createPermissionGroup());

        OrgRole createdRole = createOrgRole();

        permissionGroupRole.setOrgRole(createdRole);
        return permissionGroupRole;
    }

    private OrgRole createOrgRole(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);

        var orgRoleGroup = randomOrgRoleGroup(created).createRoleGroup();

        OrgRole orgRole = randomOrgRole(orgRoleGroup);
        orgRole.setCompany(created);
        return orgRole.createRole();
    }




}
