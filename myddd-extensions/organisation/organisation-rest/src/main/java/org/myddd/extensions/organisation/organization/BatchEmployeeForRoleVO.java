package org.myddd.extensions.organisation.organization;

import java.io.Serializable;
import java.util.List;

public class BatchEmployeeForRoleVO implements Serializable {

    private List<Long> employeeIds;

    private Long roleId;

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
