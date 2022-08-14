package org.myddd.extensions.organization.application;

import org.myddd.extensions.organisation.EmployeeNotExistsException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.organisation.domain.*;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.organization.application.assembler.EmployeeAssembler;
import org.myddd.extensions.organization.application.assembler.OrgRoleAssembler;
import org.myddd.extensions.organization.application.assembler.OrganizationAssembler;
import com.google.common.base.Strings;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.utils.Page;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class EmployeeApplicationSpring implements EmployeeApplication {

    private QueryChannelService queryChannelService;

    private QueryChannelService getQueryChannelService(){
        if(Objects.isNull(queryChannelService)){
            queryChannelService = InstanceFactory.getInstance(QueryChannelService.class);
        }
        return queryChannelService;
    }

    private UserApplication userApplication;

    private UserApplication getUserApplication(){
        if(Objects.isNull(userApplication)){
            userApplication = InstanceFactory.getInstance(UserApplication.class);
        }
        return userApplication;
    }


    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto request) {
        var deptOrgIds = request.getOrganizationList().stream().map(OrganizationDto::getId).collect(Collectors.toList());
        Employee employee = Objects.requireNonNull(EmployeeAssembler.toEmployee(request));
        var createdEmployee = EmployeeService.createEmployeeForOrg(request.getOrgId(),employee,deptOrgIds);
        return EmployeeAssembler.toDto(createdEmployee);
    }

    @Override
    public OptionalEmployeeDto queryEmployee(Int64Value request) {
        Employee queryEmployee = Employee.queryEmployeeById(request.getValue());
        if(Objects.isNull(queryEmployee))return OptionalEmployeeDto.getDefaultInstance();

        var organizationList = EmployeeOrganizationRelation.queryEmployeeOrganizations(queryEmployee.getId()).stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList());
        var roleList = EmployeeRoleAssignment.queryEmployeeRoles(queryEmployee.getId()).stream().map(OrgRoleAssembler::toOrgRoleDto).collect(Collectors.toList());
        return EmployeeAssembler.toOptionalDto(queryEmployee,roleList,organizationList);
    }

    @Override
    @Transactional
    public BoolValue assignEmployeeToOrganization(AssignEmployeeToOrganizationDto request) {
        Organization organization = Organization.queryOrganizationByOrgId(request.getOrgId());
        if(Objects.isNull(organization)){
            throw new OrganizationNotExistsException(request.getOrgId());
        }

        Employee employee = Employee.queryEmployeeById(request.getEmployeeId());
        if(Objects.isNull(employee)){
            throw new EmployeeNotExistsException(request.getEmployeeId());
        }

        EmployeeOrganizationRelation.assignEmployeeToOrganization(employee, organization);
        return BoolValue.of(true);
    }

    @Override
    public PageEmployeeDto pageQueryEmployeesByOrg(PageQueryDto request) {
         Page<EmployeeOrganizationRelation> pageResult =  getQueryChannelService()
                 .createJpqlQuery("from EmployeeOrganizationRelation eor where eor.organization.id = :orgId",EmployeeOrganizationRelation.class)
                 .addParameter("orgId",request.getOrgId())
                 .setPage(request.getPage(),request.getPageSize())
                 .pagedList();

         //查询雇员的所有角色信息
         var employeeIds = pageResult.getData().stream().map(it -> it.getEmployee().getId()).collect(Collectors.toList());
         var roleAssignments = getQueryChannelService()
                 .createJpqlQuery("from EmployeeRoleAssignment where employee.id in :employeeIds", EmployeeRoleAssignment.class)
                 .addParameter("employeeIds",employeeIds)
                 .list();
         var employeeRolesMap = roleAssignments.stream().collect(Collectors.groupingBy(it -> it.getEmployee().getId(), Collectors.mapping(it-> OrgRoleAssembler.toOrgRoleDto(it.getOrgRole()),Collectors.toList())));

         //查询雇员的所有组织信息
         var employeeOrganizations = getQueryChannelService()
                 .createJpqlQuery("from EmployeeOrganizationRelation where employee.id in :employeeIds",EmployeeOrganizationRelation.class)
                 .addParameter("employeeIds",employeeIds)
                 .list();
         var allOrganizations = employeeOrganizations.stream().map(EmployeeOrganizationRelation::getOrganization).collect(Collectors.toList());
         Organization.fetchOrganizationFullNamePath(allOrganizations);
         var employeeOrganizationMap = employeeOrganizations.stream().collect(Collectors.groupingBy(it -> it.getEmployee().getId(), Collectors.mapping(it -> OrganizationAssembler.toDTO(it.getOrganization()),Collectors.toList())));
         List<EmployeeDto> employeeDtoList = pageResult.getData().stream().map(it -> EmployeeAssembler.toDto(it.getEmployee(),employeeRolesMap.get(it.getEmployee().getId()),employeeOrganizationMap.get(it.getEmployee().getId()))).collect(Collectors.toList());

         return PageEmployeeDto.newBuilder()
                .addAllEmployees(employeeDtoList)
                .setPage(pageResult.getPageIndex())
                .setPageSize(pageResult.getPageSize())
                .setTotal(pageResult.getResultCount())
                .build();
    }



    @Override
    public PageEmployeeDto pageQueryAllEmployeesInOrg(EmployeePageQueryDto request) {
        Page<Employee> pageResult;
        StringBuilder querySQL = new StringBuilder(" from Employee where");
        Map<String,Object> parameters = new HashMap<>();

        boolean hasWhere = false;
        //如果没有指定组织范围限制，则查询所有组织下的范围
        if(request.getOrganizationLimitsList().isEmpty()){
            querySQL.append(" orgId = :orgId ");
            parameters.put("orgId",request.getOrgId());
            hasWhere = true;
        }

        if(!Strings.isNullOrEmpty(request.getSearch())){
            if(hasWhere)querySQL.append(" and ");
            querySQL.append(" name like :search");
            parameters.put("search","%"+request.getSearch()+"%");
            hasWhere = true;
        }

        boolean hasEmployeesLimits = !request.getEmployeeLimitsList().isEmpty();
        boolean hasRolesLimits = !request.getRoleLimitsList().isEmpty();
        boolean hasOrganizationsLimits = !request.getOrganizationLimitsList().isEmpty();

        boolean hasLimits = hasEmployeesLimits || hasRolesLimits || hasOrganizationsLimits;

        if(hasLimits){
            if(hasWhere)querySQL.append(" and ");
            querySQL.append(" (");
        }

        if(!request.getEmployeeLimitsList().isEmpty()){
            querySQL.append(" id in :ids");
            parameters.put("ids",request.getEmployeeLimitsList());
        }

        if(!request.getRoleLimitsList().isEmpty()){
            if(hasEmployeesLimits) querySQL.append(" or ");
            querySQL.append(" id = ANY(select ers.employee.id from EmployeeRoleAssignment ers where ers.orgRole.id in :roleIds) ");
            parameters.put("roleIds",request.getRoleLimitsList());
        }

        if(!request.getOrganizationLimitsList().isEmpty()){
            if(hasEmployeesLimits || hasRolesLimits) querySQL.append(" or ");
            querySQL.append(" id = ANY(select er.employee.id from EmployeeOrganizationRelation er where ");
            for (int i = 0; i < request.getOrganizationLimitsCount(); i++) {
                var orgId = request.getOrganizationLimits(i);
                if(i!=0)querySQL.append(" or ");
                querySQL.append(" er.path like '%/").append(orgId).append("%'");
            }
            querySQL.append(")");
        }

        if(hasLimits){
            querySQL.append(")");
        }

        pageResult = getQueryChannelService()
                .createJpqlQuery(querySQL.toString(),Employee.class)
                .setParameters(parameters)
                .setPage(request.getPage(),request.getPageSize())
                .pagedList();

        var employeeIds = pageResult.getData().stream().map(Employee::getId).collect(Collectors.toList());

        var roleAssignments = getQueryChannelService()
                .createJpqlQuery("from EmployeeRoleAssignment where employee.id in :employeeIds", EmployeeRoleAssignment.class)
                .addParameter("employeeIds",employeeIds)
                .list();
        var employeeRolesMap = roleAssignments.stream().collect(Collectors.groupingBy(it -> it.getEmployee().getId(), Collectors.mapping(it-> OrgRoleAssembler.toOrgRoleDto(it.getOrgRole()),Collectors.toList())));

        var employeeOrganizations = getQueryChannelService()
                .createJpqlQuery("from EmployeeOrganizationRelation where employee.id in :employeeIds",EmployeeOrganizationRelation.class)
                .addParameter("employeeIds",employeeIds)
                .list();
        var allOrganizations = employeeOrganizations.stream().map(EmployeeOrganizationRelation::getOrganization).collect(Collectors.toList());
        Organization.fetchOrganizationFullNamePath(allOrganizations);
        var employeeOrganizationMap = employeeOrganizations.stream().collect(Collectors.groupingBy(it -> it.getEmployee().getId(), Collectors.mapping(it -> OrganizationAssembler.toDTO(it.getOrganization()),Collectors.toList())));
        List<EmployeeDto> employeeDtoList = pageResult.getData().stream().map(it -> EmployeeAssembler.toDto(it,employeeRolesMap.get(it.getId()),employeeOrganizationMap.get(it.getId()))).collect(Collectors.toList());
        return PageEmployeeDto.newBuilder()
                .addAllEmployees(employeeDtoList)
                .setPage(pageResult.getPageIndex())
                .setPageSize(pageResult.getPageSize())
                .setTotal(pageResult.getResultCount())
                .build();
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(EmployeeDto request) {

        var employee = EmployeeAssembler.toEmployee(request);
        var subOrgIds = request.getOrganizationList().stream().map(OrganizationDto::getId).collect(Collectors.toList());
        var roleIds = request.getRoleList().stream().map(OrgRoleDto::getId).collect(Collectors.toList());

        assert employee != null;
        var updated = EmployeeService.updateEmployee(employee,subOrgIds,roleIds);
        return EmployeeAssembler.toDto(updated);
    }


    @Override
    public OptionalEmployeeDto queryEmployeeByUserIdAndOrgId(QueryEmployeeByUserAndOrg request) {
        Employee employee = Employee.queryEmployeeByUserIdAndOrgId(request.getUserId(),request.getOrgId());
        return EmployeeAssembler.toOptionalDto(employee);
    }

    @Override
    @Transactional
    public BoolValue reAssignEmployeesToOrganizations(ReAssignEmployeesToOrganizationsDto request) {
         EmployeeOrganizationRelation.reAssignEmployeeListToOrganization(request.getEmployeeIdListList(),request.getOrgIdListList());
        return BoolValue.of(true);
    }
}
