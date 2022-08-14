package org.myddd.extensions.organization.application.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organization.api.*;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class OrganizationApplicationGrpcImpl extends OrganizationApplicationGrpc.OrganizationApplicationImplBase {

    private static OrganizationApplication organizationApplication;

    public static OrganizationApplication getOrganizationApplication() {
        if(Objects.isNull(organizationApplication)){
            organizationApplication = InstanceFactory.getInstance(OrganizationApplication.class);
        }
        return organizationApplication;
    }

    @Override
    public void createDepartment(OrganizationDto request, StreamObserver<OrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().createDepartment(request));
    }

    @Override
    public void createTopOrganization(OrganizationDto request, StreamObserver<OrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().createTopOrganization(request));
    }

    @Override
    public void deleteOrganization(Int64Value request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().deleteOrganization(request));
    }

    @Override
    public void joinOrganization(JoinOrLeaveOrganizationDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().joinOrganization(request));
    }

    @Override
    public void leaveOrganization(JoinOrLeaveOrganizationDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().leaveOrganization(request));
    }

    @Override
    public void listCompanyTrees(QueryCompanyTreeDto request, StreamObserver<ListOrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().listCompanyTrees(request));
    }

    @Override
    public void pageQueryOrganizations(PageQueryDto request, StreamObserver<PageOrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().pageQueryOrganizations(request));
    }

    @Override
    public void pageSearchOrganizations(PageQueryDto request, StreamObserver<PageOrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().pageSearchOrganizations(request));
    }

    @Override
    public void queryDepartmentsByEmployeeAndOrg(QueryDepartmentsDto request, StreamObserver<ListOrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().queryDepartmentsByEmployeeAndOrg(request));
    }

    @Override
    public void queryOpenApiEmployeeDto(Int64Value request, StreamObserver<EmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().queryOpenApiEmployeeDto(request));
    }

    @Override
    public void queryOrganizationSystemManagerEmployees(Int64Value request, StreamObserver<ListEmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().queryOrganizationSystemManagerEmployees(request));
    }

    @Override
    public void updateOrganization(OrganizationDto request, StreamObserver<OrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().updateOrganization(request));
    }

    @Override
    public void queryTopCompaniesByUserId(Int64Value request, StreamObserver<ListOrganizationDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrganizationApplication().queryTopCompaniesByUserId(request));
    }
}
