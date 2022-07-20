package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限组，一个权限组是组织角色，组织以及雇员的一个集合
 */
@Entity
@Table(name = "permission_group_",
        indexes = {@Index(columnList = "type_"), @Index(columnList = "relate_id_")}
)
public class PermissionGroup extends BaseDistributedEntity {

    @Column(name = "type_", nullable = false)
    private PermissionGroupType type;

    @Column(name = "relate_id_", nullable = false)
    private String relateId;

    @Transient
    private Set<OrgRole> roleSet = new HashSet<>();

    @Transient
    private Set<Organization> organizations = new HashSet<>();

    @Transient
    private Set<Employee> employees = new HashSet<>();

    public PermissionGroupType getType() {
        return type;
    }

    public void setType(PermissionGroupType type) {
        this.type = type;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public Set<OrgRole> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<OrgRole> roleSet) {
        if(Objects.nonNull(roleSet)) this.roleSet = roleSet;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if(Objects.nonNull(organizations)) this.organizations = organizations;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setEmployees(Set<Employee> employees) {
        if(Objects.nonNull(employees))this.employees = employees;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private static PermissionGroupRepository repository;

    private static PermissionGroupRepository getRepository() {
        if (Objects.isNull(repository)) {
            repository = InstanceFactory.getInstance(PermissionGroupRepository.class);
        }
        return repository;
    }

    public PermissionGroup createPermissionGroup() {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(relateId), "关联ID不能为空");
        Preconditions.checkArgument(Objects.nonNull(type), "关联type不能为空");

        return getRepository().save(this);
    }

    public static boolean permissionGroupExists(Long permissionGroupId){
        return Objects.nonNull(getRepository().get(PermissionGroup.class,permissionGroupId));
    }

    public boolean containEmployee(Long employeeId){
        return employees.stream().map(BaseDistributedEntity::getId).collect(Collectors.toList()).contains(employeeId);
    }

    public boolean containsAnyOfRoleInList(List<OrgRole> roleList){
        for (OrgRole orgRole : roleList) {
            if (roleSet.contains(orgRole)) {
                return true;
            }
        }
        return false;
    }

    public boolean containAnyOfOrganizationInList(List<Organization> organizationList){
        for (Organization organization:organizationList){
            if(organizations.contains(organization)){
                return true;
            }
        }
        return false;
    }
}
