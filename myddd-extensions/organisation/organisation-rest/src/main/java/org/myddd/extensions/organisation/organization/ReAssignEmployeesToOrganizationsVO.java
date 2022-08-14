package org.myddd.extensions.organisation.organization;

import java.util.List;

public class ReAssignEmployeesToOrganizationsVO {

    private List<Long> employees;

    private List<Long> organizations;

    public List<Long> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Long> employees) {
        this.employees = employees;
    }

    public List<Long> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Long> organizations) {
        this.organizations = organizations;
    }
}
