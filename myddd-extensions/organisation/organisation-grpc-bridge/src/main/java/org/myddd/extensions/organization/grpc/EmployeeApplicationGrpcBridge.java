package org.myddd.extensions.organization.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import org.myddd.extensions.organization.api.*;

import javax.inject.Named;

@Named
public class EmployeeApplicationGrpcBridge extends AbstractOrganizationGrpcBridge implements EmployeeApplication {

    @Override
    public EmployeeDto createEmployee(EmployeeDto request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.createEmployee(request);
    }

    @Override
    public OptionalEmployeeDto queryEmployee(Int64Value request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.queryEmployee(request);
    }

    @Override
    public BoolValue assignEmployeeToOrganization(AssignEmployeeToOrganizationDto request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.assignEmployeeToOrganization(request);
    }

    @Override
    public PageEmployeeDto pageQueryEmployeesByOrg(PageQueryDto request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.pageQueryEmployeesByOrg(request);
    }

    @Override
    public PageEmployeeDto pageQueryAllEmployeesInOrg(EmployeePageQueryDto request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.pageQueryAllEmployeesInOrg(request);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.updateEmployee(request);
    }

    @Override
    public OptionalEmployeeDto queryEmployeeByUserIdAndOrgId(QueryEmployeeByUserAndOrg request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.queryEmployeeByUserIdAndOrgId(request);
    }

    @Override
    public BoolValue reAssignEmployeesToOrganizations(ReAssignEmployeesToOrganizationsDto request) {
        var employeeApplicationStub = EmployeeApplicationGrpc.newBlockingStub(getManagedChannel());
        return employeeApplicationStub.reAssignEmployeesToOrganizations(request);
    }
}
