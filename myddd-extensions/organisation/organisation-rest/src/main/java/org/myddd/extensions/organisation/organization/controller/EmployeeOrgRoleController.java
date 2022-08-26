package org.myddd.extensions.organisation.organization.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.protobuf.Int64Value;
import org.myddd.extensions.organisation.organization.*;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.utils.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class EmployeeOrgRoleController {

    private static final String PAGE_SIZE_MUST_GREATER_THAN_ZERO = "每页条数必须大于0";

    private static final String CURRENT_PAGE_CAN_NOT_SMALL_THAN_ZERO = "当前页必须大于等于0";

    @Inject
    private IAuthentication authentication;

    @Inject
    private OrgRoleApplication orgRoleApplication;

    @GetMapping("/organizations/{orgId}/role-groups")
    public ResponseEntity<List<OrgRoleGroupVO>> listOrgRoleGroups(@PathVariable Long orgId){
        var list = orgRoleApplication.listQueryRoleGroupsByOrg(Int64Value.of(orgId));
        var groups = list.getGroupsList().stream().map(OrgRoleGroupVO::of).collect(Collectors.toList());
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/organizations/{orgId}/role-groups")
    public ResponseEntity<OrgRoleGroupVO> createOrgRoleGroup(@PathVariable Long orgId, @RequestBody OrgRoleGroupVO roleGroupVO){
        var request = OrgRoleGroupDto.newBuilder()
                .setName(roleGroupVO.getName())
                .setOrganization(OrganizationDto.newBuilder().setId(orgId).build())
                .build();
        var created = orgRoleApplication.createOrgRoleGroup(request);
        return ResponseEntity.ok(OrgRoleGroupVO.of(created));
    }

    @PutMapping("/organizations/{orgId}/role-groups/{groupId}")
    public ResponseEntity<OrgRoleGroupVO> updateOrgRoleGroup(@PathVariable Long orgId,@PathVariable Long groupId,@RequestBody OrgRoleGroupVO roleGroupVO){
        var request = OrgRoleGroupDto.newBuilder()
                .setId(groupId)
                .setOrganization(OrganizationDto.newBuilder().setId(orgId).build())
                .setName(roleGroupVO.getName())
                .build();

        var updated = orgRoleApplication.updateOrgRoleGroup(request);
        return ResponseEntity.ok(OrgRoleGroupVO.of(updated));
    }

    @PostMapping("/organizations/{orgId}/roles")
    public ResponseEntity<OrgRoleVO> createOrgRole(@PathVariable Long orgId, @RequestBody OrgRoleVO orgRoleVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        orgRoleVO.setCompany(new OrganizationVO(orgId));
        orgRoleVO.setCreator(authentication.loginUserId());
        OrgRoleDto created =  orgRoleApplication.createOrgRole(orgRoleVO.toDto());
        return ResponseEntity.ok(OrgRoleVO.of(created));
    }

    @PutMapping("/organizations/{orgId}/roles/{roleId}")
    public ResponseEntity<OrgRoleVO> updateOrgRole(@PathVariable Long orgId,@PathVariable Long roleId,@RequestBody OrgRoleVO orgRoleVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var builder = UpdateOrgRoleDto.newBuilder()
                .setId(roleId);
        if(!Strings.isNullOrEmpty(orgRoleVO.getName()))builder.setName(orgRoleVO.getName());
        if(Objects.nonNull(orgRoleVO.getRoleGroup()))builder.setRoleGroupId(orgRoleVO.getRoleGroup().getId());

        return ResponseEntity.ok(OrgRoleVO.of(orgRoleApplication.updateOrgRole(builder.build())));
    }

    @PutMapping("/organizations/{orgId}/roles/assigner")
    public ResponseEntity<Void> batchAssignEmployeeToRole(@PathVariable Long orgId,@RequestBody BatchEmployeeForRoleVO batchEmployeeForRoleVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var batchAssignDto = BatchEmployeeForRoleDto.newBuilder()
                .addAllEmployeeIds(batchEmployeeForRoleVO.getEmployeeIds())
                .setOrgRoleId(batchEmployeeForRoleVO.getRoleId())
                .build();

        orgRoleApplication.batchAssignEmployeesToOrgRole(batchAssignDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/organizations/{orgId}/roles/de-assigner")
    public ResponseEntity<Void> batchDeAssignEmployeeToRole(@PathVariable Long orgId,@RequestBody BatchEmployeeForRoleVO batchEmployeeForRoleVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var batchAssignDto = BatchEmployeeForRoleDto.newBuilder()
                .addAllEmployeeIds(batchEmployeeForRoleVO.getEmployeeIds())
                .setOrgRoleId(batchEmployeeForRoleVO.getRoleId())
                .build();

        orgRoleApplication.batchDeAssignEmployeesToOrlRole(batchAssignDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employees/{employeeId}/roles/{roleId}")
    public ResponseEntity<Void> assignEmployeeToOrgRole(@PathVariable Long employeeId,@PathVariable Long roleId){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        EmployeeOrgRoleRelationDto employeeOrgRoleRelationDto = EmployeeOrgRoleRelationDto
                .newBuilder()
                .setEmployeeId(employeeId)
                .setOrgRoleId(roleId)
                .build();

        orgRoleApplication.assignEmployeeToOrgRole(employeeOrgRoleRelationDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/employees/{employeeId}/roles/{roleId}")
    public ResponseEntity<Void> deAssignEmployeeFromRole(@PathVariable Long employeeId,@PathVariable Long roleId){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        EmployeeOrgRoleRelationDto employeeOrgRoleRelationDto = EmployeeOrgRoleRelationDto
                .newBuilder()
                .setEmployeeId(employeeId)
                .setOrgRoleId(roleId)
                .build();

        orgRoleApplication.deAssignEmployeeFromOrgRole(employeeOrgRoleRelationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/organizations/{orgId}/all-roles")
    public ResponseEntity<List<OrgRoleVO>> listOrgRoles(@PathVariable Long orgId){
        var list = orgRoleApplication.listOrgRoles(ListOrgQueryDto.newBuilder().setOrgId(orgId).build());
        return ResponseEntity.ok(list.getRolesList().stream().map(OrgRoleVO::of).collect(Collectors.toList()));
    }

    @PostMapping("/organizations/{orgId}/all-roles/query")
    public ResponseEntity<List<OrgRoleVO>> listOrgRolesWithLimits(@PathVariable Long orgId,@RequestBody WithLimitsQueryVO queryVO){
        var list = orgRoleApplication.listOrgRoles(
                ListOrgQueryDto.newBuilder()
                        .setOrgId(orgId)
                        .addAllLimits(queryVO.getLimits())
                        .build());
        return ResponseEntity.ok(list.getRolesList().stream().map(OrgRoleVO::of).collect(Collectors.toList()));
    }

    @GetMapping("/organizations/{orgId}/roles")
    public ResponseEntity<Page<OrgRoleVO>> pageQueryOrgRoles(@PathVariable Long orgId, @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize){
        Preconditions.checkArgument(page >= 0, CURRENT_PAGE_CAN_NOT_SMALL_THAN_ZERO);
        Preconditions.checkArgument(pageSize > 0,PAGE_SIZE_MUST_GREATER_THAN_ZERO);
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PageOrgRole pageOrgRole = orgRoleApplication.pageQueryOrgRolesByOrg(
                PageOrgRoleQueryDto.newBuilder()
                        .setOrgId(orgId)
                        .setPage(page)
                        .setPageSize(pageSize)
                        .setSearch(search)
                        .build()
        );
        return ResponseEntity.ok(
                Page.builder(OrgRoleVO.class)
                        .data(pageOrgRole.getOrgRolesList().stream().map(OrgRoleVO::of).collect(Collectors.toList()))
                        .totalSize(pageOrgRole.getTotal())
                        .pageSize(pageSize)
                        .start((long) pageSize * page)
        );
    }

    @PostMapping("/organizations/{orgId}/roles/page-query")
    public ResponseEntity<Page<OrgRoleVO>> pageQueryOrgRolesWithLimits(@PathVariable Long orgId,@RequestBody WithLimitsPageQueryVO pageQueryVO){
        Preconditions.checkArgument(pageQueryVO.getPage() >= 0, CURRENT_PAGE_CAN_NOT_SMALL_THAN_ZERO);
        Preconditions.checkArgument(pageQueryVO.getPageSize() > 0,PAGE_SIZE_MUST_GREATER_THAN_ZERO);
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PageOrgRole pageOrgRole = orgRoleApplication.pageQueryOrgRolesByOrg(
                PageOrgRoleQueryDto.newBuilder()
                        .setOrgId(orgId)
                        .setPage(pageQueryVO.getPage())
                        .setPageSize(pageQueryVO.getPageSize())
                        .setSearch(pageQueryVO.getSearch())
                        .addAllLimits(pageQueryVO.getLimits())
                        .build()
        );
        return ResponseEntity.ok(
                Page.builder(OrgRoleVO.class)
                        .data(pageOrgRole.getOrgRolesList().stream().map(OrgRoleVO::of).collect(Collectors.toList()))
                        .totalSize(pageOrgRole.getTotal())
                        .pageSize(pageOrgRole.getPageSize())
                        .start((long) pageQueryVO.getPage() * pageQueryVO.getPageSize())
        );
    }

    @GetMapping("/organizations/{orgId}/roles/{roleId}/employees")
    public ResponseEntity<Page<EmployeeVO>> pageQueryRoleEmployees(@RequestParam(defaultValue = "") String search, @PathVariable Long roleId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize){
        Preconditions.checkArgument(page >= 0, CURRENT_PAGE_CAN_NOT_SMALL_THAN_ZERO);
        Preconditions.checkArgument(pageSize > 0,PAGE_SIZE_MUST_GREATER_THAN_ZERO);

        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PageRoleEmployeeDto pageRoleEmployeeDto = orgRoleApplication.pageQueryEmployeesByRole(
                PageRoleEmployeeQueryDto.newBuilder()
                        .setRoleId(roleId)
                        .setPage(page)
                        .setPageSize(pageSize)
                        .setSearch(search)
                        .build()
        );

        return ResponseEntity.ok(
                Page.builder(EmployeeVO.class)
                        .pageSize(pageSize)
                        .start((long) page * pageSize)
                        .totalSize(pageRoleEmployeeDto.getTotal())
                        .data(pageRoleEmployeeDto.getEmployeesList().stream().map(EmployeeVO::of).collect(Collectors.toList()))
        );
    }

    @PostMapping("/organizations/{orgId}/roles/{roleId}/employees/page-query")
    public ResponseEntity<Page<EmployeeVO>> pageQueryRoleEmployeesWithLimits(@PathVariable Long roleId,@RequestBody  WithLimitsPageQueryVO queryVO){
        Preconditions.checkArgument(queryVO.getPage() >= 0, CURRENT_PAGE_CAN_NOT_SMALL_THAN_ZERO);
        Preconditions.checkArgument(queryVO.getPageSize() > 0,PAGE_SIZE_MUST_GREATER_THAN_ZERO);

        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PageRoleEmployeeDto pageRoleEmployeeDto = orgRoleApplication.pageQueryEmployeesByRole(
                PageRoleEmployeeQueryDto.newBuilder()
                        .setRoleId(roleId)
                        .setPage(queryVO.getPage())
                        .setPageSize(queryVO.getPageSize())
                        .setSearch(queryVO.getSearch())
                        .addAllLimits(queryVO.getLimits())
                        .build()
        );

        return ResponseEntity.ok(
                Page.builder(EmployeeVO.class)
                        .pageSize(queryVO.getPageSize())
                        .start((long) queryVO.getPage() * queryVO.getPageSize())
                        .totalSize(pageRoleEmployeeDto.getTotal())
                        .data(pageRoleEmployeeDto.getEmployeesList().stream().map(EmployeeVO::of).collect(Collectors.toList()))
        );
    }

    @DeleteMapping(value = "/organizations/{orgId}/role-groups/{groupId}")
    public ResponseEntity<Void> removeRoleGroup(@PathVariable Long orgId,@PathVariable Long groupId){
        Objects.requireNonNull(orgId);
        orgRoleApplication.removeRoleGroup(Int64Value.of(groupId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/organizations/{orgId}/roles/{roleId}")
    public ResponseEntity<Void> removeOrgRole(@PathVariable Long orgId,@PathVariable Long roleId){
        Objects.requireNonNull(orgId);
        orgRoleApplication.removeOrgRole(Int64Value.of(roleId));
        return ResponseEntity.ok().build();
    }

}
