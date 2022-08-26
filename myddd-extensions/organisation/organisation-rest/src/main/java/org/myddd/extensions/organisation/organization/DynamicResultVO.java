package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DynamicResultVO {

    private List<EmployeeVO> employees;

    private List<OrganizationVO> organizations;

    public static DynamicResultVO createFromEmployee(EmployeeVO employeeVO){
        var dynamicResult = new DynamicResultVO();
        dynamicResult.employees = List.of(employeeVO);
        return dynamicResult;
    }

    public static DynamicResultVO createFromOrganizations(List<OrganizationVO> organizations){
        var dynamicResult = new DynamicResultVO();
        dynamicResult.organizations = organizations;
        return dynamicResult;
    }

    public List<OrganizationVO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrganizationVO> organizations) {
        this.organizations = organizations;
    }

    public List<EmployeeVO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeVO> employees) {
        this.employees = employees;
    }
}
