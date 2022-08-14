package org.myddd.extensions.organization.application;

import org.myddd.extensions.organisation.OrgRoleNotExistsException;
import org.myddd.extensions.organisation.domain.EmployeeOrganizationRelation;
import org.myddd.extensions.organisation.domain.EmployeeRoleAssignment;
import org.myddd.extensions.organisation.domain.OrgRole;
import org.myddd.extensions.organisation.domain.OrgRoleGroup;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.organization.application.assembler.EmployeeAssembler;
import org.myddd.extensions.organization.application.assembler.OrgRoleAssembler;
import org.myddd.extensions.organization.application.assembler.OrgRoleGroupAssembler;
import org.myddd.extensions.organization.application.assembler.OrganizationAssembler;
import com.google.common.base.Strings;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.domain.InstanceFactory;
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
public class OrgRoleApplicationSpring implements OrgRoleApplication {

    private static QueryChannelService queryChannelService;

    private static QueryChannelService getQueryChannelService(){
        if(Objects.isNull(queryChannelService)){
            queryChannelService = InstanceFactory.getInstance(QueryChannelService.class);
        }
        return queryChannelService;
    }

    @Override
    @Transactional
    public OrgRoleDto createOrgRole(OrgRoleDto request) {
        OrgRole created = OrgRoleAssembler.toOrgRole(request).createRole();
        return OrgRoleAssembler.toOrgRoleDto(created);
    }

    @Override
    @Transactional
    public OrgRoleGroupDto createOrgRoleGroup(OrgRoleGroupDto request) {
        var created = Objects.requireNonNull(OrgRoleGroupAssembler.toEntity(request)).createRoleGroup();
        return OrgRoleGroupAssembler.toDto(created);
    }

    @Override
    @Transactional
    public OrgRoleGroupDto updateOrgRoleGroup(OrgRoleGroupDto request) {
        var updated = OrgRoleAssembler.toRoleGroup(request).update();
        return OrgRoleAssembler.toRoleGroupDto(updated);
    }

    @Override
    public ListOrgRoleGroupDto listQueryRoleGroupsByOrg(Int64Value request) {
        var list = getQueryChannelService()
                .createJpqlQuery("from OrgRoleGroup where company.id = :orgId", OrgRoleGroup.class)
                .addParameter("orgId",request.getValue())
                .list();

        return ListOrgRoleGroupDto.newBuilder()
                .addAllGroups(list.stream().map(OrgRoleAssembler::toRoleGroupDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public OrgRoleDto updateOrgRole(UpdateOrgRoleDto request) {
        var exists = OrgRole.queryById(request.getId());
        if(Objects.isNull(exists))throw new OrgRoleNotExistsException(request.getId());

        if(!Strings.isNullOrEmpty(request.getName())) {
            exists.setName(request.getName());
            exists.updateRole();
        }

        if(request.getRoleGroupId() > 0){
            OrgRole.changeOrgRoleGroup(exists.getId(),request.getRoleGroupId());
        }

        return OrgRoleAssembler.toOrgRoleDto(exists);
    }

    @Override
    @Transactional
    public BoolValue removeOrgRole(Int64Value request) {
        OrgRole.removeRole(request.getValue());
        return BoolValue.of(true);
    }

    @Override
    @Transactional
    public BoolValue assignEmployeeToOrgRole(EmployeeOrgRoleRelationDto request) {
        EmployeeRoleAssignment.assignEmployeeToRole(request.getEmployeeId(),request.getOrgRoleId());
        return BoolValue.of(true);
    }

    @Override
    public ListOrgRoleDto listOrgRoles(ListOrgQueryDto request) {
        var querySQL = "from OrgRole where company.id = :orgId";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("orgId",request.getOrgId());

        if(!request.getLimitsList().isEmpty()){
            querySQL += " and id in :ids";
            parameters.put("ids",request.getLimitsList());
        }

        var queryList = getQueryChannelService()
                .createJpqlQuery(querySQL,OrgRole.class)
                .setParameters(parameters)
                .list();

        return ListOrgRoleDto.newBuilder()
                .addAllRoles(queryList.stream().map(OrgRoleAssembler::toOrgRoleDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public BoolValue batchAssignEmployeesToOrgRole(BatchEmployeeForRoleDto request) {
        request.getEmployeeIdsList().forEach(it -> {
            EmployeeRoleAssignment.assignEmployeeToRole(it,request.getOrgRoleId());
        });
        return BoolValue.of(true);
    }

    @Override
    @Transactional
    public BoolValue batchDeAssignEmployeesToOrlRole(BatchEmployeeForRoleDto request) {
        request.getEmployeeIdsList().forEach(it -> {
            EmployeeRoleAssignment.deAssignEmployeeFromRole(it,request.getOrgRoleId());
        });
        return BoolValue.of(true);
    }

    @Override
    @Transactional
    public BoolValue deAssignEmployeeFromOrgRole(EmployeeOrgRoleRelationDto request) {
        EmployeeRoleAssignment.deAssignEmployeeFromRole(request.getEmployeeId(),request.getOrgRoleId());
        return BoolValue.of(true);
    }

    @Override
    public PageOrgRole pageQueryOrgRolesByOrg(PageOrgRoleQueryDto request) {
        Page<OrgRole> orgRolePage;

        var querySQL = "from OrgRole where company.id = :orgId";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("orgId",request.getOrgId());

        if(!Strings.isNullOrEmpty(request.getSearch())){
            querySQL += " and name like :search";
            parameters.put("search","%"+request.getSearch()+"%");
        }

        if(!request.getLimitsList().isEmpty()){
            querySQL += " and id in :ids";
            parameters.put("ids",request.getLimitsList());
        }

        orgRolePage = getQueryChannelService()
                .createJpqlQuery(querySQL,OrgRole.class)
                .setParameters(parameters)
                .setPage(request.getPage(),request.getPageSize())
                .pagedList();

        return PageOrgRole.newBuilder()
                .setPageSize(request.getPageSize())
                .setPage(request.getPage())
                .setTotal(orgRolePage.getResultCount())
                .addAllOrgRoles(orgRolePage.getData().stream().map(OrgRoleAssembler::toOrgRoleDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public PageRoleEmployeeDto pageQueryEmployeesByRole(PageRoleEmployeeQueryDto request) {
        Page<EmployeeRoleAssignment> employeeRoleAssignmentPage;

        var querySQL = "from EmployeeRoleAssignment where orgRole.id = :roleId";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("roleId",request.getRoleId());

        if(!Strings.isNullOrEmpty(request.getSearch())){
            querySQL += " and employee.name like :search";
            parameters.put("search","%"+request.getSearch()+"%");
        }

        if(!request.getLimitsList().isEmpty()){
            querySQL += " and id in :ids";
            parameters.put("ids",request.getLimitsList());
        }

        employeeRoleAssignmentPage = getQueryChannelService()
                .createJpqlQuery(querySQL,EmployeeRoleAssignment.class)
                .setParameters(parameters)
                .setPage(request.getPage(),request.getPageSize())
                .pagedList();


        var idList = employeeRoleAssignmentPage.getData().stream().map(it -> it.getEmployee().getId()).collect(Collectors.toList());

        var employeeOrganizationMap = EmployeeOrganizationRelation.batchQueryEmployeeOrganizations(idList);


        return PageRoleEmployeeDto.newBuilder()
                .setPageSize(request.getPageSize())
                .setPage(request.getPage())
                .setTotal(employeeRoleAssignmentPage.getResultCount())
                .addAllEmployees(employeeRoleAssignmentPage.getData().stream().map(it -> {
                    var employee = it.getEmployee();
                    var organizationList = employeeOrganizationMap.get(employee.getId());
                    if(Objects.isNull(organizationList)) organizationList = List.of();

                    var organizations = organizationList.stream().map(OrganizationAssembler::toDTO).collect(Collectors.toList());
                    return EmployeeAssembler.toDto(it.getEmployee(), List.of(),organizations);
                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public BoolValue removeRoleGroup(Int64Value request) {
        OrgRoleGroup.removeOrgRoleGroup(request.getValue());
        return BoolValue.of(true);
    }

    @Override
    @Transactional
    public BoolValue changeOrgRoleGroup(ChangeOrgRoleGroupDto request) {
        OrgRole.changeOrgRoleGroup(request.getRoleId(),request.getGroupId());
        return BoolValue.of(true);
    }
}
