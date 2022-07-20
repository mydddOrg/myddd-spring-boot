package org.myddd.extensions.organisation.domain;

import com.google.protobuf.Int64Value;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.organisation.UserNotFoundException;
import org.myddd.extensions.organisation.UserNotFoundInOrgException;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@Named
public class OrganizationService {

    @Inject
    private UserApplication userApplication;

    public Organization createTopOrganization(Company company){
        Organization createdOrganization = company.createTopCompany();
        UserDto userDto = queryUserDtoByUserId(company.getCreateUserId());
        if(Objects.isNull(userDto)){
            throw new UserNotFoundException(company.getCreateUserId());
        }
        Employee createEmployee = employeeFromUser(userDto,createdOrganization.getId()).createEmployee();
        EmployeeOrganizationRelation.assignEmployeeToOrganization(createEmployee, createdOrganization);
        return createdOrganization;
    }

    public void joinOrganization(Long userId,long orgId){
        var exists = Employee.queryEmployeeByUserIdAndOrgId(userId,orgId);
        if(Objects.nonNull(exists)) return;

        UserDto userDto = queryUserDtoByUserId(userId);
        if(Objects.isNull(userDto)){
            throw new UserNotFoundException(userId);
        }
        Organization organization = Organization.queryOrganizationByOrgId(orgId);
        if(Objects.isNull(organization)){
            throw new OrganizationNotExistsException(orgId);
        }

        Employee createEmployee = employeeFromUser(userDto,orgId).createEmployee();
        EmployeeOrganizationRelation.assignEmployeeToOrganization(createEmployee, organization);
    }

    public boolean leaveOrganization(Long userId,long orgId){
        Employee employee = Employee.queryEmployeeByUserIdAndOrgId(userId,orgId);
        if(Objects.isNull(employee)){
            throw new UserNotFoundInOrgException(userId,orgId);
        }

        EmployeeOrganizationRelation.deAssignEmployeeFromOrganization(employee.getId(), orgId);
        return true;
    }

    private Employee employeeFromUser(UserDto userDto,long orgId){
        Employee employee = new Employee();
        employee.setUserId(userDto.getId());
        employee.setRelateUserId(userDto.getUserId());
        employee.setName(userDto.getName());
        employee.setPhone(userDto.getPhone());
        employee.setEmail(userDto.getEmail());
        employee.setOrgId(orgId);
        employee.setCreateTime(System.currentTimeMillis());
        return employee;
    }

    private UserDto queryUserDtoByUserId(Long userId){
        return userApplication.queryUserById(Int64Value.of(userId));
    }
}
