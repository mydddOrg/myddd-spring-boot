package org.myddd.extensions.organization.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.extensions.organization.api.*;

import javax.inject.Named;

@Named
public class OrganizationApplicationGrpcBridge extends AbstractOrganizationGrpcBridge implements OrganizationApplication {

    @Override
    public OrganizationDto createTopOrganization(OrganizationDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.createTopOrganization(request);
    }

    @Override
    public OrganizationDto createDepartment(OrganizationDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.createDepartment(request);
    }

    @Override
    public BoolValue joinOrganization(JoinOrLeaveOrganizationDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.joinOrganization(request);
    }

    @Override
    public BoolValue leaveOrganization(JoinOrLeaveOrganizationDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.leaveOrganization(request);
    }

    @Override
    public BoolValue deleteOrganization(Int64Value request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.deleteOrganization(request);
    }

    @Override
    public ListOrganizationDto queryTopCompaniesByUserId(Int64Value request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.queryTopCompaniesByUserId(request);
    }

    @Override
    public ListOrganizationDto queryDepartmentsByEmployeeAndOrg(QueryDepartmentsDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.queryDepartmentsByEmployeeAndOrg(request);
    }

    @Override
    public ListOrganizationDto listCompanyTrees(QueryCompanyTreeDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.listCompanyTrees(request);
    }

    @Override
    public PageOrganizationDto pageSearchOrganizations(PageQueryDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.pageSearchOrganizations(request);
    }

    @Override
    public PageOrganizationDto pageQueryOrganizations(PageQueryDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.pageQueryOrganizations(request);
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.updateOrganization(request);
    }

    @Override
    public EmployeeDto queryOpenApiEmployeeDto(Int64Value request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.queryOpenApiEmployeeDto(request);
    }

    @Override
    public ListEmployeeDto queryOrganizationSystemManagerEmployees(Int64Value request) {
        var organizationApplicationStub = OrganizationApplicationGrpc.newBlockingStub(getManagedChannel());
        return organizationApplicationStub.queryOrganizationSystemManagerEmployees(request);
    }
}
