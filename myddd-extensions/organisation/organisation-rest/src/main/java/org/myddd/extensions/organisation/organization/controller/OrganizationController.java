package org.myddd.extensions.organisation.organization.controller;

import com.google.protobuf.Int64Value;
import org.myddd.extensions.organisation.organization.JoinOrLeaveOrganizationVO;
import org.myddd.extensions.organisation.organization.OrganizationVO;
import org.myddd.extensions.organisation.organization.WithLimitsPageQueryVO;
import org.myddd.extensions.organisation.organization.WithLimitsQueryVO;
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
import java.util.stream.Collectors;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class OrganizationController {

    @Inject
    private IAuthentication authentication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private EmployeeApplication employeeApplication;

    @GetMapping("/users/{userId}/organizations")
    ResponseEntity<List<OrganizationVO>> queryTopCompaniesByUserId(@PathVariable Long userId){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ListOrganizationDto listOrganizationDto = organizationApplication.queryTopCompaniesByUserId(Int64Value.of(userId));
        List<OrganizationVO> organizationVOList = listOrganizationDto.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList());
        return ResponseEntity.ok(organizationVOList);
    }

    @GetMapping("/current-login-user/organizations")
    ResponseEntity<List<OrganizationVO>> queryCurrentLoginTopCompaniesByUserId(){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userId = authentication.loginUserId();
        ListOrganizationDto listOrganizationDto = organizationApplication.queryTopCompaniesByUserId(Int64Value.of(userId));
        List<OrganizationVO> organizationVOList = listOrganizationDto.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList());
        return ResponseEntity.ok(organizationVOList);
    }

    @GetMapping("/organizations/{orgId}/employees/{employeeId}/departments")
    ResponseEntity<List<OrganizationVO>> queryEmployeeDepartments(@PathVariable Long orgId,@PathVariable Long employeeId,@RequestParam(defaultValue = "") String search,@RequestParam(value = "include-sub",defaultValue = "false") boolean includeSub){
        var queryDto = QueryDepartmentsDto.newBuilder()
                .setEmployeeId(employeeId)
                .setOrgId(orgId)
                .setIncludeSubOrg(includeSub)
                .setSearch(search)
                .build();

        var queryList = organizationApplication.queryDepartmentsByEmployeeAndOrg(queryDto);
        return ResponseEntity.ok(queryList.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList()));
    }

    @PostMapping("/organizations")
    ResponseEntity<OrganizationVO> createOrganization(@RequestBody OrganizationVO organizationVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        organizationVO.setCreateUserId(authentication.loginUserId());

        OrganizationDto organizationDto = organizationVO.toDTO();
        OrganizationDto created;
        if(organizationDto.getParentId() > 0){
            created = organizationApplication.createDepartment(organizationDto);
        }else{
            created = organizationApplication.createTopOrganization(organizationDto);
        }
        return ResponseEntity.ok(OrganizationVO.of(created));
    }

    @PutMapping("/organizations/{orgId}/join")
    ResponseEntity<Void> joinOrganization(@PathVariable Long orgId,@RequestBody JoinOrLeaveOrganizationVO joinOrLeaveOrganizationVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        joinOrLeaveOrganizationVO.setOrgId(orgId);
        joinOrLeaveOrganizationVO.setOperator(authentication.loginUserId());

        boolean success = organizationApplication.joinOrganization(joinOrLeaveOrganizationVO.toDto()).getValue();

        if(success){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/organizations/{orgId}/organizations")
    ResponseEntity<Page<OrganizationVO>> pageQuerySubOrganizations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize, @PathVariable long orgId){

        PageQueryDto queryDto = PageQueryDto.newBuilder()
                .setPageSize(pageSize)
                .setPage(page)
                .setOrgId(orgId)
                .build();

        PageOrganizationDto pageOrganizationDto = organizationApplication.pageQueryOrganizations(queryDto);
        return ResponseEntity.ok(
                Page.builder(OrganizationVO.class)
                        .start((long) page * pageSize)
                        .pageSize(pageOrganizationDto.getPageSize())
                        .totalSize(pageOrganizationDto.getTotal())
                        .data(pageOrganizationDto.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList()))
        );
    }

    @GetMapping("/organizations/{orgId}/tree")
    ResponseEntity<List<OrganizationVO>> listCompanyTrees(@PathVariable long orgId){
        var query = organizationApplication.listCompanyTrees(QueryCompanyTreeDto.newBuilder().setOrgId(orgId).build());
        return ResponseEntity.ok(query.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList()));
    }

    @PostMapping("/organizations/{orgId}/tree/query")
    ResponseEntity<List<OrganizationVO>> listCompanyTreesWithLimits(@PathVariable long orgId,@RequestBody WithLimitsQueryVO queryVO){
        var query = organizationApplication.listCompanyTrees(QueryCompanyTreeDto.newBuilder()
                .setOrgId(orgId)
                .addAllLimits(queryVO.getLimits())
                .build());
        return ResponseEntity.ok(query.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList()));
    }

    @GetMapping("/organizations/{orgId}/searcher")
    ResponseEntity<Page<OrganizationVO>> pageSearchOrganizations(@PathVariable long orgId,@RequestParam(defaultValue = "") String search,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "100") int pageSize){
        PageQueryDto queryDto = PageQueryDto.newBuilder()
                .setPageSize(pageSize)
                .setPage(page)
                .setOrgId(orgId)
                .setSearch(search)
                .build();

        PageOrganizationDto pageOrganizationDto = organizationApplication.pageSearchOrganizations(queryDto);
        return ResponseEntity.ok(
                Page.builder(OrganizationVO.class)
                        .start((long) page * pageSize)
                        .pageSize(pageOrganizationDto.getPageSize())
                        .totalSize(pageOrganizationDto.getTotal())
                        .data(pageOrganizationDto.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList()))
        );
    }

    @PostMapping("/organizations/{orgId}/searcher/page-query")
    ResponseEntity<Page<OrganizationVO>> pageSearchOrganizationsWithLimits(@PathVariable long orgId, @RequestBody WithLimitsPageQueryVO pageQueryVO){
        PageQueryDto queryDto = PageQueryDto.newBuilder()
                .setPageSize(pageQueryVO.getPageSize())
                .setPage(pageQueryVO.getPage())
                .setOrgId(orgId)
                .setSearch(pageQueryVO.getSearch())
                .addAllLimits(pageQueryVO.getLimits())
                .build();

        PageOrganizationDto pageOrganizationDto = organizationApplication.pageSearchOrganizations(queryDto);
        return ResponseEntity.ok(
                Page.builder(OrganizationVO.class)
                        .start(pageOrganizationDto.getPage() * pageOrganizationDto.getPageSize())
                        .pageSize(pageOrganizationDto.getPageSize())
                        .totalSize(pageOrganizationDto.getTotal())
                        .data(pageOrganizationDto.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList()))
        );
    }

    @PutMapping("/organizations/{orgId}")
    ResponseEntity<OrganizationVO> updateOrganization(@PathVariable Long orgId, @RequestBody OrganizationVO organizationVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        organizationVO.setId(orgId);
        organizationVO.setCreateUserId(authentication.loginUserId());
        OrganizationDto organizationDto = organizationVO.toDTO();
        OrganizationDto updated = organizationApplication.updateOrganization(organizationDto);
        return ResponseEntity.ok(OrganizationVO.of(updated));
    }


    @DeleteMapping("/organizations/{orgId}")
    ResponseEntity<Void> deleteOrganization(@PathVariable Long orgId){
        organizationApplication.deleteOrganization(Int64Value.of(orgId));
        return ResponseEntity.ok().build();
    }

}
