package org.myddd.extensions.organisation.domain;

import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.PermissionGroupNotExistsException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PermissionGroupService {

    private PermissionGroupService(){}

    private static PermissionGroupRepository repository;

    private static PermissionGroupRepository getRepository() {
        if (Objects.isNull(repository)) {
            repository = InstanceFactory.getInstance(PermissionGroupRepository.class);
        }
        return repository;
    }

    public static void removePermissionGroupById(Long permissionGroupId) {
        getRepository().removePermissionGroupById(permissionGroupId);
    }

    public static void clearPermissionGroupById(Long permissionGroupId) {
        getRepository().clearPermissionGroupId(permissionGroupId);
    }

    public static List<PermissionGroup> queryEmployeePermissionGroupInRelateScope(PermissionGroupType permissionGroupType, String relateId, Long employeeId) {
        List<PermissionGroup> permissionGroups = listPermissionGroupByTypeAndRelateId(permissionGroupType, relateId);
        List<Organization> allEmployeeOrganizations = EmployeeOrganizationRelation.queryEmployeeAllOrganizations(employeeId);
        List<OrgRole> orgRoles = EmployeeRoleAssignment.queryEmployeeRoles(employeeId);

        return permissionGroups.stream()
                .filter(it -> it.containEmployee(employeeId) || it.containAnyOfOrganizationInList(allEmployeeOrganizations) || it.containsAnyOfRoleInList(orgRoles))
                .collect(Collectors.toList());
    }

    public static PermissionGroup queryPermissionGroup(Long permissionGroupId) {
        PermissionGroup permissionGroup = getRepository().get(PermissionGroup.class, permissionGroupId);
        if (Objects.isNull(permissionGroup)) {
            throw new PermissionGroupNotExistsException(permissionGroupId);
        }
        permissionGroup.setEmployees(getRepository().queryPermissionGroupEmployeesById(permissionGroupId));
        permissionGroup.setRoleSet(getRepository().queryPermissionGroupRolesById(permissionGroupId));
        permissionGroup.setOrganizations(getRepository().queryPermissionGroupOrganizationsById(permissionGroupId));
        return permissionGroup;
    }


    public static List<PermissionGroup> listPermissionGroupByTypeAndRelateId(PermissionGroupType type, String relateId) {
        List<PermissionGroup> permissionGroups = getRepository().listPermissionGroupByTypeAndRelateId(type, relateId);

        Set<Long> permissionGroupIds = permissionGroups.stream().map(BaseDistributedEntity::getId).collect(Collectors.toSet());
        Map<Long, Set<OrgRole>> orgRoleMap = getRepository().batchQueryPermissionGroupRolesByIds(permissionGroupIds);
        Map<Long, Set<Employee>> employeeMap = getRepository().batchQueryPermissionGroupEmployeesByIds(permissionGroupIds);
        Map<Long, Set<Organization>> organizationMap = getRepository().batchQueryPermissionGroupOrganizationsByIds(permissionGroupIds);

        permissionGroups.forEach(permissionGroup -> {
            permissionGroup.setOrganizations(organizationMap.get(permissionGroup.getId()));
            permissionGroup.setEmployees(employeeMap.get(permissionGroup.getId()));
            permissionGroup.setRoleSet(orgRoleMap.get(permissionGroup.getId()));
        });

        return permissionGroups;
    }

}
