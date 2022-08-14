package org.myddd.extensions.organization.application.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organization.api.*;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class EmployeeApplicationGrpcImpl extends EmployeeApplicationGrpc.EmployeeApplicationImplBase {


    private static EmployeeApplication employeeApplication;

    private static EmployeeApplication getEmployeeApplication(){
        if(Objects.isNull(employeeApplication)){
            employeeApplication = InstanceFactory.getInstance(EmployeeApplication.class);
        }
        return employeeApplication;
    }

    @Override
    public void createEmployee(EmployeeDto request, StreamObserver<EmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()-> getEmployeeApplication().createEmployee(request));
    }

    @Override
    public void assignEmployeeToOrganization(AssignEmployeeToOrganizationDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()-> getEmployeeApplication().assignEmployeeToOrganization(request));
    }

    @Override
    public void pageQueryAllEmployeesInOrg(EmployeePageQueryDto request, StreamObserver<PageEmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()-> getEmployeeApplication().pageQueryAllEmployeesInOrg(request));
    }

    @Override
    public void queryEmployee(Int64Value request, StreamObserver<OptionalEmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()-> getEmployeeApplication().queryEmployee(request));
    }

    @Override
    public void pageQueryEmployeesByOrg(PageQueryDto request, StreamObserver<PageEmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()-> getEmployeeApplication().pageQueryEmployeesByOrg(request));
    }

    @Override
    public void reAssignEmployeesToOrganizations(ReAssignEmployeesToOrganizationsDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getEmployeeApplication().reAssignEmployeesToOrganizations(request));
    }

    @Override
    public void updateEmployee(EmployeeDto request, StreamObserver<EmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getEmployeeApplication().updateEmployee(request));
    }

    @Override
    public void queryEmployeeByUserIdAndOrgId(QueryEmployeeByUserAndOrg request, StreamObserver<OptionalEmployeeDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getEmployeeApplication().queryEmployeeByUserIdAndOrgId(request));
    }
}
