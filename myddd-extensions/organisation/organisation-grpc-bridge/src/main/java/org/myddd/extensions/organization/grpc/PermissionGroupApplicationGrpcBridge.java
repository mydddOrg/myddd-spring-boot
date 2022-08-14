package org.myddd.extensions.organization.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.extensions.organization.api.*;

import javax.inject.Named;

@Named
public class PermissionGroupApplicationGrpcBridge extends AbstractOrganizationGrpcBridge implements PermissionGroupApplication {
    @Override
    public PermissionGroupDto createPermissionGroup(PermissionGroupDto request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.createPermissionGroup(request);
    }

    @Override
    public PermissionGroupDto queryPermissionGroup(Int64Value request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.queryPermissionGroup(request);
    }

    @Override
    public PermissionGroupDto updatePermissionGroup(PermissionGroupDto request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.updatePermissionGroup(request);
    }

    @Override
    public ListPermissionGroupDto listQueryPermissionGroups(ListQueryPermissionGroupDto request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.listQueryPermissionGroups(request);
    }

    @Override
    public BoolValue deletePermissionGroup(RemovePermissionGroupDto request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.deletePermissionGroup(request);
    }

    @Override
    public ListPermissionGroupDto queryEmployeePermissionGroups(QueryEmployeePermissionGroupDto request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.queryEmployeePermissionGroups(request);
    }

    @Override
    public BoolValue checkEmployeeInPermissionGroup(EmployeeInPermissionGroupDto request) {
        var permissionGroupApplicationStub = PermissionGroupApplicationGrpc.newBlockingStub(getManagedChannel());
        return permissionGroupApplicationStub.checkEmployeeInPermissionGroup(request);
    }
}
