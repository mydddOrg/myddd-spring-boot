package org.myddd.extensions.organisation.domain;

import org.myddd.domain.BaseDistributedEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "permission_group_employee_")
public class PermissionGroupEmployee extends BaseDistributedEntity {

    @ManyToOne
    @JoinColumn(name = "permission_group_id_")
    private PermissionGroup permissionGroup;

    @ManyToOne
    @JoinColumn(name = "employee_id_")
    private Employee employee;

    public PermissionGroup getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(PermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionGroupEmployee)) return false;
        if (!super.equals(o)) return false;
        PermissionGroupEmployee that = (PermissionGroupEmployee) o;
        return Objects.equals(permissionGroup, that.permissionGroup) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), permissionGroup, employee);
    }
}
