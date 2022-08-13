package org.myddd.extensions.security.application.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.api.*;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class UserApplicationGrpcProxy extends UserApplicationGrpc.UserApplicationImplBase {

    private static UserApplication userApplication;

    public static UserApplication getUserApplication() {
        if(Objects.isNull(userApplication)){
            userApplication = InstanceFactory.getInstance(UserApplication.class);
        }
        return userApplication;
    }

    @Override
    public void createLocalUser(UserDto request, StreamObserver<UserDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserApplication().createLocalUser(request));
    }

    @Override
    public void disableUser(UserDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserApplication().disableUser(request));
    }

    @Override
    public void enableUser(UserDto request, StreamObserver<BoolValue> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserApplication().enableUser(request));
    }

    @Override
    public void queryLocalUserByUserId(StringValue request, StreamObserver<UserDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserApplication().queryLocalUserByUserId(request));
    }

    @Override
    public void pageQueryUser(PageQueryDto request, StreamObserver<PageUserDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserApplication().pageQueryUser(request));
    }

    @Override
    public void queryUserById(Int64Value request, StreamObserver<UserDto> responseObserver) {
        GrpcRunner.run(responseObserver,()->getUserApplication().queryUserById(request));
    }


}
