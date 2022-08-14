package org.myddd.extensions.organization.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.extensions.organization.api.*;

import javax.inject.Named;

@Named
public class OrgRoleApplicationGrpcBridge extends AbstractOrganizationGrpcBridge implements OrgRoleApplication {
    @Override
    public OrgRoleDto createOrgRole(OrgRoleDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.createOrgRole(request);
    }

    @Override
    public OrgRoleGroupDto createOrgRoleGroup(OrgRoleGroupDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.createOrgRoleGroup(request);
    }

    @Override
    public OrgRoleGroupDto updateOrgRoleGroup(OrgRoleGroupDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.updateOrgRoleGroup(request);
    }

    @Override
    public ListOrgRoleGroupDto listQueryRoleGroupsByOrg(Int64Value request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.listQueryRoleGroupsByOrg(request);
    }

    @Override
    public OrgRoleDto updateOrgRole(UpdateOrgRoleDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.updateOrgRole(request);
    }

    @Override
    public BoolValue removeOrgRole(Int64Value request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.removeOrgRole(request);
    }

    @Override
    public BoolValue assignEmployeeToOrgRole(EmployeeOrgRoleRelationDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.assignEmployeeToOrgRole(request);
    }

    @Override
    public ListOrgRoleDto listOrgRoles(ListOrgQueryDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.listOrgRoles(request);
    }

    @Override
    public BoolValue batchAssignEmployeesToOrgRole(BatchEmployeeForRoleDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.batchAssignEmployeesToOrgRole(request);
    }

    @Override
    public BoolValue batchDeAssignEmployeesToOrlRole(BatchEmployeeForRoleDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.batchDeAssignEmployeesToOrlRole(request);
    }

    @Override
    public BoolValue deAssignEmployeeFromOrgRole(EmployeeOrgRoleRelationDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.deAssignEmployeeFromOrgRole(request);
    }

    @Override
    public PageOrgRole pageQueryOrgRolesByOrg(PageOrgRoleQueryDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.pageQueryOrgRolesByOrg(request);
    }

    @Override
    public PageRoleEmployeeDto pageQueryEmployeesByRole(PageRoleEmployeeQueryDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.pageQueryEmployeesByRole(request);
    }

    @Override
    public BoolValue removeRoleGroup(Int64Value request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.removeRoleGroup(request);
    }

    @Override
    public BoolValue changeOrgRoleGroup(ChangeOrgRoleGroupDto request) {
        var orgRoleApplicationStub = OrgRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return orgRoleApplicationStub.changeOrgRoleGroup(request);
    }
}
