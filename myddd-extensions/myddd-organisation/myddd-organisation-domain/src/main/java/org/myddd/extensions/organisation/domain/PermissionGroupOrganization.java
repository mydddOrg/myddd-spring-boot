package org.myddd.extensions.organisation.domain;

import org.myddd.domain.BaseDistributedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "permission_group_organization_")
public class PermissionGroupOrganization extends BaseDistributedEntity {

    @ManyToOne
    @JoinColumn(name = "permission_group_id_")
    private PermissionGroup permissionGroup;

    @ManyToOne
    @JoinColumn(name = "organization_id_")
    private Organization organization;

    public PermissionGroup getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(PermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionGroupOrganization)) return false;
        if (!super.equals(o)) return false;
        PermissionGroupOrganization that = (PermissionGroupOrganization) o;
        return Objects.equals(permissionGroup, that.permissionGroup) && Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), permissionGroup, organization);
    }
}
