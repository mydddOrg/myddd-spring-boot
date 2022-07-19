package org.myddd.extensions.organisation.domain;

import org.myddd.domain.AbstractRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionGroupRepository extends AbstractRepository {

    /**
     * 批量更新权限组--角色
     * @param permissionGroupRoles 角色集
     */
    void batchSavePermissionGroupRole(Set<PermissionGroupRole> permissionGroupRoles);

    /**
     * 批量更新权限组--组织
     * @param permissionGroupOrganizations 组织集
     */
    void batchSavePermissionGroupOrganization(Set<PermissionGroupOrganization> permissionGroupOrganizations);

    /**
     * 批量更新权限组--雇员
     * @param permissionGroupEmployees 雇员集
     */
    void batchSavePermissionGroupEmployee(Set<PermissionGroupEmployee> permissionGroupEmployees);

    /**
     * 查询一个权限组的关联角色信息
     * @param permissionGroupId 权限组ID
     * @return 返回角色集
     */
    Set<OrgRole> queryPermissionGroupRolesById(Long permissionGroupId);

    /**
     * 查询一个权限组关联的雇员信息
     * @param permissionGroupId 权限组ID
     * @return 雇员集
     */
    Set<Employee> queryPermissionGroupEmployeesById(Long permissionGroupId);

    /**
     * 查询一个权限组关联的组织集
     * @param permissionGroupId 权限组ID
     * @return 组织集
     */
    Set<Organization> queryPermissionGroupOrganizationsById(Long permissionGroupId);

    /**
     * 批量查询权限组的角色集
     * @param permissionGroupIds 权限组ID集
     * @return 返回Map,key为权限组ID,Value为角色集
     */
    Map<Long,Set<OrgRole>> batchQueryPermissionGroupRolesByIds(Set<Long> permissionGroupIds);

    /**
     * 批量查询权限组的雇员集
     * @param permissionGroupIds 权限组ID集
     * @return 返回Map,Key为权限组ID，Value为雇员集
     */
    Map<Long,Set<Employee>> batchQueryPermissionGroupEmployeesByIds(Set<Long> permissionGroupIds);

    /**
     * 批量查询权限组的组织集
     * @param permissionGroupIds 权限组ID集
     * @return 返回Map,Key为权限组ID,Value为组织集
     */
    Map<Long,Set<Organization>> batchQueryPermissionGroupOrganizationsByIds(Set<Long> permissionGroupIds);

    /**
     * 根据关联ID及type类型，查询对应的权限组集
     * @param type 权限组类型
     * @param relateId 关联ID
     * @return 返回权限组集
     */
    List<PermissionGroup> listPermissionGroupByTypeAndRelateId(PermissionGroupType type,String relateId);

    /**
     * 删除一个权限组
     * @param permissionGroupId 权限组ID
     */
    void removePermissionGroupById(Long permissionGroupId);


    void clearPermissionGroupId(Long permissionGroupId);
}
