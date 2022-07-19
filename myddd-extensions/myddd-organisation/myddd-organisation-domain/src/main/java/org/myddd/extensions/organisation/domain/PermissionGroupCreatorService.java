package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.EmployeeNotExistsException;
import org.myddd.extensions.organisation.OrgRoleNotExistsException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.organisation.PermissionGroupNotExistsException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PermissionGroupCreatorService {

    private PermissionGroupCreatorService(){}

    private static PermissionGroupRepository repository;

    private static PermissionGroupRepository getRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(PermissionGroupRepository.class);
        }
        return repository;
    }

    public static PermissionGroup updatePermissionGroup(PermissionGroupCreator permissionGroupCreator){
        PermissionGroup permissionGroup = getRepository().get(PermissionGroup.class,permissionGroupCreator.id);
        if(Objects.isNull(permissionGroup)){
            throw new PermissionGroupNotExistsException(permissionGroupCreator.id);
        }

        permissionGroup.setOrganizations(createPermissionGroupOrganization(permissionGroupCreator, permissionGroup));
        permissionGroup.setEmployees(createPermissionEmployee(permissionGroupCreator, permissionGroup));
        permissionGroup.setRoleSet(createPermissionGroupRole(permissionGroupCreator, permissionGroup));
        return permissionGroup;
    }

    public static PermissionGroup createPermissionGroup(PermissionGroupCreator permissionGroupCreator){
        Preconditions.checkArgument(
                !permissionGroupCreator.employeeIds.isEmpty() ||
                        !permissionGroupCreator.organizationIds.isEmpty() ||
                        !permissionGroupCreator.roleIds.isEmpty(),
                "关联的角色，组织或雇员不能同时为空"
        );
        PermissionGroup createdPermissionGroup = createPermissionGroupEntity(permissionGroupCreator);
        createdPermissionGroup.setOrganizations(createPermissionGroupOrganization(permissionGroupCreator, createdPermissionGroup));
        createdPermissionGroup.setEmployees(createPermissionEmployee(permissionGroupCreator, createdPermissionGroup));
        createdPermissionGroup.setRoleSet(createPermissionGroupRole(permissionGroupCreator, createdPermissionGroup));
        return createdPermissionGroup;
    }

    private static Set<OrgRole> createPermissionGroupRole(PermissionGroupCreator permissionGroupCreator, PermissionGroup createdPermissionGroup) {
        Set<PermissionGroupRole> permissionGroupRoleSet = new HashSet<>();
        Set<OrgRole> orgRoleSet = new HashSet<>();

        permissionGroupCreator.roleIds.forEach(roleId -> {
            OrgRole role = OrgRole.queryById(roleId);
            if(Objects.isNull(role)){
                throw new OrgRoleNotExistsException(roleId);
            }
            orgRoleSet.add(role);

            PermissionGroupRole permissionGroupRole = new PermissionGroupRole();
            permissionGroupRole.setPermissionGroup(createdPermissionGroup);
            permissionGroupRole.setOrgRole(role);
            permissionGroupRoleSet.add(permissionGroupRole);
        });

        getRepository().batchSavePermissionGroupRole(permissionGroupRoleSet);

        return orgRoleSet;
    }

    private static Set<Employee> createPermissionEmployee(PermissionGroupCreator permissionGroupCreator, PermissionGroup createdPermissionGroup) {
        Set<PermissionGroupEmployee> permissionGroupEmployeeSet = new HashSet<>();
        Set<Employee> employeeSet = new HashSet<>();

        permissionGroupCreator.employeeIds.forEach(employeeId -> {
            Employee employee = Employee.queryEmployeeById(employeeId);
            if(Objects.isNull(employee)){
                throw new EmployeeNotExistsException(employeeId);
            }
            employeeSet.add(employee);

            PermissionGroupEmployee permissionGroupEmployee = new PermissionGroupEmployee();
            permissionGroupEmployee.setEmployee(employee);
            permissionGroupEmployee.setPermissionGroup(createdPermissionGroup);
            permissionGroupEmployeeSet.add(permissionGroupEmployee);
        });

        getRepository().batchSavePermissionGroupEmployee(permissionGroupEmployeeSet);

        return employeeSet;
    }

    private static Set<Organization> createPermissionGroupOrganization(PermissionGroupCreator permissionGroupCreator, PermissionGroup createdPermissionGroup) {
        Set<PermissionGroupOrganization> permissionGroupOrganizationSet = new HashSet<>();
        Set<Organization> organizationSet = new HashSet<>();
        permissionGroupCreator.organizationIds.forEach(organizationId -> {
            Organization organization = Organization.queryOrganizationByOrgId(organizationId);
            if(Objects.isNull(organization)){
                throw new OrganizationNotExistsException(organizationId);
            }
            organizationSet.add(organization);

            PermissionGroupOrganization permissionGroupOrganization = new PermissionGroupOrganization();
            permissionGroupOrganization.setPermissionGroup(createdPermissionGroup);
            permissionGroupOrganization.setOrganization(organization);
            permissionGroupOrganizationSet.add(permissionGroupOrganization);
        });
        getRepository().batchSavePermissionGroupOrganization(permissionGroupOrganizationSet);
        return organizationSet;
    }

    private static PermissionGroup createPermissionGroupEntity(PermissionGroupCreator permissionGroupCreator) {
        PermissionGroup permissionGroup = new PermissionGroup();
        if(Objects.nonNull(permissionGroupCreator.id) && permissionGroupCreator.id > 0)permissionGroup.setId(permissionGroupCreator.id);
        permissionGroup.setType(permissionGroupCreator.type);
        permissionGroup.setRelateId(permissionGroupCreator.relationId);
        return permissionGroup.createPermissionGroup();
    }

    public static class PermissionGroupCreator {

        private Long id;

        private PermissionGroupType type;

        private String relationId;

        private Set<Long> roleIds = new HashSet<>();

        private Set<Long> employeeIds = new HashSet<>();

        private Set<Long> organizationIds = new HashSet<>();

        public static PermissionGroupCreator createInstance() {
            return new PermissionGroupCreator();
        }

        public PermissionGroupCreator id(Long id){
            this.id = id;
            return this;
        }
        public PermissionGroupCreator type(PermissionGroupType type) {
            this.type = type;
            return this;
        }

        public PermissionGroupCreator relationId(String relationId) {
            this.relationId = relationId;
            return this;
        }

        public PermissionGroupCreator roleIds(Set<Long> roleIds) {
            this.roleIds = roleIds;
            return this;
        }

        public PermissionGroupCreator employeeIds(Set<Long> employeeIds) {
            this.employeeIds = employeeIds;
            return this;
        }

        public PermissionGroupCreator organizationIds(Set<Long> organizationIds) {
            this.organizationIds = organizationIds;
            return this;
        }
    }
}
