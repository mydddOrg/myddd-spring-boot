package org.myddd.extensions.organisation;

import org.myddd.extensions.organisation.domain.*;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class PermissionGroupRepositoryJPA extends AbstractRepositoryJPA implements PermissionGroupRepository {

    public static final String PERMISSION_GROUP_ID = "permissionGroupId";
    public static final String GROUP_IDS = "permissionGroupIds";

    @Override
    public void batchSavePermissionGroupRole(Set<PermissionGroupRole> permissionGroupRoles) {
        batchSaveEntities(permissionGroupRoles);
    }

    @Override
    public void batchSavePermissionGroupOrganization(Set<PermissionGroupOrganization> permissionGroupOrganizations) {
        batchSaveEntities(permissionGroupOrganizations);
    }

    @Override
    public void batchSavePermissionGroupEmployee(Set<PermissionGroupEmployee> permissionGroupEmployees) {
        batchSaveEntities(permissionGroupEmployees);
    }

    @Override
    public Set<OrgRole> queryPermissionGroupRolesById(Long permissionGroupId) {
        List<PermissionGroupRole> permissionGroupRoles =  getEntityManager()
                .createQuery("from PermissionGroupRole where permissionGroup.id = :permissionGroupId",PermissionGroupRole.class)
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .getResultList();

        return permissionGroupRoles.stream().map(PermissionGroupRole::getOrgRole).collect(Collectors.toSet());
    }

    @Override
    public Set<Employee> queryPermissionGroupEmployeesById(Long permissionGroupId) {
        List<PermissionGroupEmployee> employees = getEntityManager()
                .createQuery("from PermissionGroupEmployee where permissionGroup.id = :permissionGroupId",PermissionGroupEmployee.class)
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .getResultList();
        return employees.stream().map(PermissionGroupEmployee::getEmployee).collect(Collectors.toSet());
    }

    @Override
    public Set<Organization> queryPermissionGroupOrganizationsById(Long permissionGroupId) {
        List<PermissionGroupOrganization> organizations = getEntityManager()
                .createQuery("from PermissionGroupOrganization where permissionGroup.id = :permissionGroupId",PermissionGroupOrganization.class)
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .getResultList();
        return organizations.stream().map(PermissionGroupOrganization::getOrganization).collect(Collectors.toSet());
    }

    @Override
    public Map<Long, Set<OrgRole>> batchQueryPermissionGroupRolesByIds(Set<Long> permissionGroupIds) {
        List<PermissionGroupRole> roles = getEntityManager()
                .createQuery("from PermissionGroupRole where permissionGroup.id IN :permissionGroupIds",PermissionGroupRole.class)
                .setParameter(GROUP_IDS,permissionGroupIds)
                .getResultList();

        Map<Long, Set<OrgRole>> roleSetMap = new HashMap<>();
        for (PermissionGroupRole role : roles) {
            Long permissionGroupId = role.getPermissionGroup().getId();
            Set<OrgRole> roleSet = roleSetMap.containsKey(permissionGroupId)?roleSetMap.get(permissionGroupId):new HashSet<>();
            roleSet.add(role.getOrgRole());
            roleSetMap.put(permissionGroupId,roleSet);
        }
        return roleSetMap;
    }

    @Override
    public Map<Long, Set<Employee>> batchQueryPermissionGroupEmployeesByIds(Set<Long> permissionGroupIds) {
        List<PermissionGroupEmployee> employeeLst = getEntityManager()
                .createQuery("from PermissionGroupEmployee where permissionGroup.id IN :permissionGroupIds",PermissionGroupEmployee.class)
                .setParameter(GROUP_IDS,permissionGroupIds)
                .getResultList();

        Map<Long, Set<Employee>> employeeMapSet = new HashMap<>();
        for (PermissionGroupEmployee permissionGroupEmployee : employeeLst) {
            Long permissionGroupId = permissionGroupEmployee.getPermissionGroup().getId();
            Set<Employee> employeeSet = employeeMapSet.containsKey(permissionGroupId)?employeeMapSet.get(permissionGroupId):new HashSet<>();
            employeeSet.add(permissionGroupEmployee.getEmployee());
            employeeMapSet.put(permissionGroupId,employeeSet);
        }
        return employeeMapSet;
    }

    @Override
    public Map<Long, Set<Organization>> batchQueryPermissionGroupOrganizationsByIds(Set<Long> permissionGroupIds) {
        List<PermissionGroupOrganization> organizations = getEntityManager()
                .createQuery("from PermissionGroupOrganization where permissionGroup.id in :permissionGroupIds",PermissionGroupOrganization.class)
                .setParameter(GROUP_IDS,permissionGroupIds)
                .getResultList();

        Map<Long, Set<Organization>> organizationMapSet = new HashMap<>();
        for (PermissionGroupOrganization permissionGroupOrganization : organizations) {
            Long permissionId = permissionGroupOrganization.getPermissionGroup().getId();
            Set<Organization> organizationSet = organizationMapSet.containsKey(permissionId)?organizationMapSet.get(permissionId):new HashSet<>();
            organizationSet.add(permissionGroupOrganization.getOrganization());

            organizationMapSet.put(permissionId,organizationSet);
        }
        return organizationMapSet;
    }

    @Override
    public List<PermissionGroup> listPermissionGroupByTypeAndRelateId(PermissionGroupType type, String relateId) {
        return getEntityManager()
                .createQuery("from PermissionGroup where type = :type and relateId = :relateId",PermissionGroup.class)
                .setParameter("type",type)
                .setParameter("relateId",relateId)
                .getResultList();
    }


    @Override
    public void removePermissionGroupById(Long permissionGroupId) {

        clearPermissionGroupId(permissionGroupId);

        //清理permissionGroup本身
        getEntityManager()
                .createQuery("delete from PermissionGroup where id = :permissionGroupId")
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .executeUpdate();
    }

    @Override
    public void clearPermissionGroupId(Long permissionGroupId) {
        //清理ROLE
        getEntityManager()
                .createQuery("delete from PermissionGroupRole where permissionGroup.id = :permissionGroupId")
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .executeUpdate();

        //清理雇员
        getEntityManager()
                .createQuery("delete from PermissionGroupEmployee where permissionGroup.id = :permissionGroupId")
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .executeUpdate();

        //清理关联的组织
        getEntityManager()
                .createQuery("delete from PermissionGroupOrganization where permissionGroup.id = :permissionGroupId")
                .setParameter(PERMISSION_GROUP_ID,permissionGroupId)
                .executeUpdate();
    }
}
