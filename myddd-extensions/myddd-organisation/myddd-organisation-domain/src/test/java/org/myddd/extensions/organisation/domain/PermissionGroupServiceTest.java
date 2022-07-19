package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.extensions.organisation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
class PermissionGroupServiceTest extends AbstractTest {

    private Company createdCompany;

    private void createCompany(){
        createdCompany = randomCompany().createTopCompany();
    }

    @Test
    void testEmptyCreatorCreatePermissionGroup(){
        PermissionGroupCreatorService.PermissionGroupCreator notValidCreator = PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                .type(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM)
                .relationId(randomId());
        Assertions.assertThrows(IllegalArgumentException.class,() -> PermissionGroupCreatorService.createPermissionGroup(notValidCreator));
    }

    @Test
    void testNotValidEmployeeCreatePermissionGroup(){
        PermissionGroupCreatorService.PermissionGroupCreator notValidCreator = PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                .type(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM)
                .relationId(randomId());

        notValidCreator.employeeIds(Set.of(-1L,100L));
        Assertions.assertThrows(EmployeeNotExistsException.class,() -> PermissionGroupCreatorService.createPermissionGroup(notValidCreator));
    }

    @Test
    void testNotValidOrganizationCreatePermissionGroup(){
        PermissionGroupCreatorService.PermissionGroupCreator notValidCreator = PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                .type(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM)
                .relationId(randomId());

        notValidCreator.organizationIds(Set.of(-1L,100L));
        Assertions.assertThrows(OrganizationNotExistsException.class,() -> PermissionGroupCreatorService.createPermissionGroup(notValidCreator));
    }

    @Test
    void testNotValidOrgRoleCreatePermissionGroup(){
        PermissionGroupCreatorService.PermissionGroupCreator notValidCreator = PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                .type(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM)
                .relationId(randomId());

        notValidCreator.roleIds(Set.of(-1L,100L));
        Assertions.assertThrows(OrgRoleNotExistsException.class,() -> PermissionGroupCreatorService.createPermissionGroup(notValidCreator));
    }


    @Test
    void testCreatePermissionGroup(){
        Assertions.assertNotNull(createPermissionGroup());
    }

    @Test
    void testQueryEmployeePermissionGroup(){
        PermissionGroup created = createPermissionGroup();
        Assertions.assertTrue(PermissionGroupService.queryEmployeePermissionGroupInRelateScope(created.getType(),created.getRelateId(),-1L).isEmpty());

        List<PermissionGroup> employeePermissionGroup = PermissionGroupService.queryEmployeePermissionGroupInRelateScope(created.getType(),created.getRelateId(),created.getEmployees().stream().findFirst().get().getId());
        Assertions.assertFalse(employeePermissionGroup.isEmpty());

        Organization randomOrg = created.getOrganizations().stream().findAny().get();
        Employee employee = randomEmployee(randomOrg.getId()).createEmployee();
        EmployeeOrganizationRelation.assignEmployeeToOrganization(employee, randomOrg);
        employeePermissionGroup = PermissionGroupService.queryEmployeePermissionGroupInRelateScope(created.getType(),created.getRelateId(),employee.getId());
        Assertions.assertFalse(employeePermissionGroup.isEmpty());
    }

    @Test
    void testUpdatePermissionGroup(){
        PermissionGroup created = createPermissionGroup();

        PermissionGroupCreatorService.PermissionGroupCreator updateDto = PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                .id(created.getId())
                .type(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM)
                .relationId(randomId());

        Set<Organization> organizations = Set.of(createOrganization());
        updateDto.organizationIds(organizations.stream().map(BaseDistributedEntity::getId).collect(Collectors.toSet()));

        Assertions.assertDoesNotThrow(() -> PermissionGroupCreatorService.updatePermissionGroup(updateDto) );
    }

    @Test
    void testDeletePermissionGroup(){
        Assertions.assertDoesNotThrow(()-> PermissionGroupService.removePermissionGroupById(-1L));

        PermissionGroup created = createPermissionGroup();
        Assertions.assertDoesNotThrow(() -> PermissionGroupService.removePermissionGroupById(created.getId()));
    }

    @Test
    void testQueryPermissionGroup(){
        Assertions.assertThrows(PermissionGroupNotExistsException.class,()->PermissionGroupService.queryPermissionGroup(-1L));

        PermissionGroup created = createPermissionGroup();
        Assertions.assertNotNull(PermissionGroupService.queryPermissionGroup(created.getId()));
    }

    @Test
    void testListPermissionGroupByTypeAndRelateId(){
        Assertions.assertTrue(PermissionGroupService.listPermissionGroupByTypeAndRelateId(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM,randomId()).isEmpty());

        PermissionGroup created = createPermissionGroup();

        List<PermissionGroup> permissionGroups = PermissionGroupService.listPermissionGroupByTypeAndRelateId(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM,created.getRelateId());
        Assertions.assertFalse(permissionGroups.isEmpty());

        PermissionGroup first = permissionGroups.stream().findFirst().get();
        Assertions.assertFalse(first.getRoleSet().isEmpty());
        Assertions.assertFalse(first.getOrganizations().isEmpty());
        Assertions.assertFalse(first.getEmployees().isEmpty());

    }

    private PermissionGroup createPermissionGroup(){
        PermissionGroupCreatorService.PermissionGroupCreator creator = PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                .type(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM)
                .relationId(randomId());

        createCompany();

        Set<Employee> employees = Set.of(createEmployee(),createEmployee());
        Set<Organization> organizations = Set.of(createOrganization());
        Set<OrgRole> orgRoles = Set.of(createOrgRole(),createOrgRole(),createOrgRole());

        creator.employeeIds(employees.stream().map(BaseDistributedEntity::getId).collect(Collectors.toSet()));
        creator.organizationIds(organizations.stream().map(BaseDistributedEntity::getId).collect(Collectors.toSet()));
        creator.roleIds(orgRoles.stream().map(BaseDistributedEntity::getId).collect(Collectors.toSet()));

        return PermissionGroupCreatorService.createPermissionGroup(creator);
    }

    private Employee createEmployee(){
        return randomEmployee().createEmployee();
    }

    private Organization createOrganization(){
        return randomDepartment(createdCompany).createDepartment();
    }

    private OrgRole createOrgRole(){
        OrgRole orgRole = randomOrgRole();
        orgRole.setCompany(createdCompany);
        return orgRole.createRole();
    }
}
