package org.myddd.extensions.organization.application.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organization.api.*;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class OrgRoleApplicationGrpcImpl extends OrgRoleApplicationGrpc.OrgRoleApplicationImplBase {

    private static OrgRoleApplication orgRoleApplication;

    public static OrgRoleApplication getOrgRoleApplication() {
        if(Objects.isNull(orgRoleApplication)){
            orgRoleApplication = InstanceFactory.getInstance(OrgRoleApplication.class);
        }
        return orgRoleApplication;
    }

    @Override
    public void assignEmployeeToOrgRole(EmployeeOrgRoleRelationDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().assignEmployeeToOrgRole(request));
    }

    @Override
    public void createOrgRole(OrgRoleDto request, StreamObserver<OrgRoleDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().createOrgRole(request));
    }

    @Override
    public void batchAssignEmployeesToOrgRole(BatchEmployeeForRoleDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().batchAssignEmployeesToOrgRole(request));
    }

    @Override
    public void changeOrgRoleGroup(ChangeOrgRoleGroupDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().changeOrgRoleGroup(request));
    }

    @Override
    public void createOrgRoleGroup(OrgRoleGroupDto request, StreamObserver<OrgRoleGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().createOrgRoleGroup(request));
    }

    @Override
    public void listOrgRoles(ListOrgQueryDto request, StreamObserver<ListOrgRoleDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().listOrgRoles(request));
    }

    @Override
    public void pageQueryOrgRolesByOrg(PageOrgRoleQueryDto request, StreamObserver<PageOrgRole> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().pageQueryOrgRolesByOrg(request));
    }

    @Override
    public void removeOrgRole(Int64Value request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().removeOrgRole(request));
    }

    @Override
    public void batchDeAssignEmployeesToOrlRole(BatchEmployeeForRoleDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().batchDeAssignEmployeesToOrlRole(request));
    }

    @Override
    public void deAssignEmployeeFromOrgRole(EmployeeOrgRoleRelationDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().deAssignEmployeeFromOrgRole(request));
    }

    @Override
    public void listQueryRoleGroupsByOrg(Int64Value request, StreamObserver<ListOrgRoleGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().listQueryRoleGroupsByOrg(request));
    }

    @Override
    public void updateOrgRole(UpdateOrgRoleDto request, StreamObserver<OrgRoleDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().updateOrgRole(request));
    }

    @Override
    public void updateOrgRoleGroup(OrgRoleGroupDto request, StreamObserver<OrgRoleGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().updateOrgRoleGroup(request));
    }

    @Override
    public void pageQueryEmployeesByRole(PageRoleEmployeeQueryDto request, StreamObserver<PageRoleEmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().pageQueryEmployeesByRole(request));
    }

    @Override
    public void removeRoleGroup(Int64Value request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getOrgRoleApplication().removeRoleGroup(request));
    }
}
