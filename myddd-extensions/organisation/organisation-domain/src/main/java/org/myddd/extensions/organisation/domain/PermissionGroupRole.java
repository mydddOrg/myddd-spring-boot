package org.myddd.extensions.organisation.domain;

import org.myddd.domain.BaseDistributedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "permission_group_role_")
public class PermissionGroupRole extends BaseDistributedEntity {

    @ManyToOne
    @JoinColumn(name = "permission_group_id_")
    private PermissionGroup permissionGroup;

    @ManyToOne
    @JoinColumn(name = "org_id_")
    private OrgRole orgRole;

    public PermissionGroup getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(PermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public OrgRole getOrgRole() {
        return orgRole;
    }

    public void setOrgRole(OrgRole orgRole) {
        this.orgRole = orgRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionGroupRole)) return false;
        if (!super.equals(o)) return false;
        PermissionGroupRole that = (PermissionGroupRole) o;
        return Objects.equals(permissionGroup, that.permissionGroup) && Objects.equals(orgRole, that.orgRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), permissionGroup, orgRole);
    }
}
