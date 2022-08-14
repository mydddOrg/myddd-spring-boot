package org.myddd.extensions.organization.application.assembler;

import com.google.common.base.Strings;
import org.myddd.extensions.organisation.domain.Employee;
import org.myddd.extensions.organization.api.EmployeeDto;
import org.myddd.extensions.organization.api.OptionalEmployeeDto;
import org.myddd.extensions.organization.api.OrgRoleDto;
import org.myddd.extensions.organization.api.OrganizationDto;

import java.util.List;
import java.util.Objects;

public class EmployeeAssembler {

    public static Employee toEmployee(EmployeeDto employeeDto) {
        if (Objects.isNull(employeeDto)) return null;
        Employee employee = new Employee();
        if (employeeDto.getId() > 0) employee.setId(employeeDto.getId());
        employee.setUserId(employeeDto.getUserId());
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setRelateUserId(employeeDto.getRelateUserId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setOrgId(employeeDto.getOrgId());
        return employee;
    }

    public static OptionalEmployeeDto toOptionalDto(Employee employee){
        if (Objects.isNull(employee)) return OptionalEmployeeDto.getDefaultInstance();
        return OptionalEmployeeDto.newBuilder().setEmployee(toDto(employee)).build();
    }

    public static EmployeeDto toDto(Employee employee) {
        if (Objects.isNull(employee)) return null;

        var builder = EmployeeDto.newBuilder();
        builder
                .setId(employee.getId())
                .setRelateUserId(employee.getRelateUserId())
                .setName(employee.getName())
                .setOrgId(employee.getOrgId());
        if(Objects.nonNull(employee.getUserId()))builder.setUserId(employee.getUserId());
        if(!Strings.isNullOrEmpty(employee.getEmployeeId()))builder.setEmployeeId(employee.getEmployeeId());
        if (!Strings.isNullOrEmpty(employee.getEmail())) builder.setEmail(employee.getEmail());
        if (!Strings.isNullOrEmpty(employee.getPhone())) builder.setPhone(employee.getPhone());
        if(Objects.nonNull(employee.getDataSource()))builder.setDataSource(employee.getDataSource().toString());
        return toDto(employee,null,null);
    }

    public static OptionalEmployeeDto toOptionalDto(Employee employee, List<OrgRoleDto> roleDtoList, List<OrganizationDto> organizationDtoList){
        if (Objects.isNull(employee)) return OptionalEmployeeDto.getDefaultInstance();
        return OptionalEmployeeDto.newBuilder().setEmployee(toDto(employee,roleDtoList,organizationDtoList)).build();
    }
    public static EmployeeDto toDto(Employee employee, List<OrgRoleDto> roleDtoList, List<OrganizationDto> organizationDtoList) {
        if (Objects.isNull(employee)) return null;

        var builder = EmployeeDto.newBuilder();
        builder
                .setId(employee.getId())
                .setRelateUserId(employee.getRelateUserId())
                .setName(employee.getName())
                .setOrgId(employee.getOrgId());
        if(Objects.nonNull(employee.getUserId()))builder.setUserId(employee.getUserId());
        if(!Strings.isNullOrEmpty(employee.getEmployeeId()))builder.setEmployeeId(employee.getEmployeeId());
        if (!Strings.isNullOrEmpty(employee.getEmail())) builder.setEmail(employee.getEmail());
        if (!Strings.isNullOrEmpty(employee.getPhone())) builder.setPhone(employee.getPhone());
        if(Objects.nonNull(roleDtoList))builder.addAllRole(roleDtoList);
        if(Objects.nonNull(employee.getDataSource()))builder.setDataSource(employee.getDataSource().toString());
        if(Objects.nonNull(organizationDtoList))builder.addAllOrganization(organizationDtoList);
        return builder.build();
    }
}
