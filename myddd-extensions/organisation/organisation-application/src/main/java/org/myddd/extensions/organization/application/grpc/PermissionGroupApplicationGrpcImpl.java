package org.myddd.extensions.organization.application.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organization.api.*;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class PermissionGroupApplicationGrpcImpl extends PermissionGroupApplicationGrpc.PermissionGroupApplicationImplBase {

    private static PermissionGroupApplication permissionGroupApplication;

    public static PermissionGroupApplication getPermissionGroupApplication() {
        if(Objects.isNull(permissionGroupApplication)){
            permissionGroupApplication = InstanceFactory.getInstance(PermissionGroupApplication.class);
        }
        return permissionGroupApplication;
    }

    @Override
    public void checkEmployeeInPermissionGroup(EmployeeInPermissionGroupDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().checkEmployeeInPermissionGroup(request));
    }

    @Override
    public void createPermissionGroup(PermissionGroupDto request, StreamObserver<PermissionGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().createPermissionGroup(request));
    }

    @Override
    public void deletePermissionGroup(RemovePermissionGroupDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().deletePermissionGroup(request));
    }

    @Override
    public void listQueryPermissionGroups(ListQueryPermissionGroupDto request, StreamObserver<ListPermissionGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().listQueryPermissionGroups(request));
    }

    @Override
    public void queryEmployeePermissionGroups(QueryEmployeePermissionGroupDto request, StreamObserver<ListPermissionGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().queryEmployeePermissionGroups(request));
    }

    @Override
    public void queryPermissionGroup(Int64Value request, StreamObserver<PermissionGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().queryPermissionGroup(request));
    }

    @Override
    public void updatePermissionGroup(PermissionGroupDto request, StreamObserver<PermissionGroupDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getPermissionGroupApplication().updatePermissionGroup(request));
    }

}
