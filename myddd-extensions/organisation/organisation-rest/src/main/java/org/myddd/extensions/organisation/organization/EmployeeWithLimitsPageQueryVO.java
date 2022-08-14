package org.myddd.extensions.organisation.organization;

import java.util.List;

public class EmployeeWithLimitsPageQueryVO {

    private int page = 0;

    private int pageSize = 100;

    private String search = "";

    private List<Long> employees = List.of();

    private List<Long> organizations = List.of();

    private List<Long> roles = List.of();

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

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
