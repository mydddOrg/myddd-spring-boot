package org.myddd.extensions.security.application.grpc;

import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.api.UserRoleApplication;
import org.myddd.extensions.security.api.UserRoleApplicationGrpc;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class UserRoleApplicationGrpcProxy extends UserRoleApplicationGrpc.UserRoleApplicationImplBase {

    private static UserRoleApplication userRoleApplication;

    public static UserRoleApplication getUserRoleApplication() {
        if(Objects.isNull(userRoleApplication)){
            userRoleApplication = InstanceFactory.getInstance(UserRoleApplication.class);
        }
        return userRoleApplication;
    }

    @Override
    public void createRole(RoleDto request, StreamObserver<RoleDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserRoleApplication().createRole(request));
    }

}
