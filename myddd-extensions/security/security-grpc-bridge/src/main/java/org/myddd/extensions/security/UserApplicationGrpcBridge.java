package org.myddd.extensions.security;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import org.myddd.extensions.security.api.*;

import javax.inject.Named;

@Named
public class UserApplicationGrpcBridge extends AbstractSecurityGrpcBridge implements UserApplication {

    @Override
    public UserDto createLocalUser(UserDto request) {
        var userApplicationStub = UserApplicationGrpc.newBlockingStub(getManagedChannel());
        return userApplicationStub.createLocalUser(request);
    }

    @Override
    public OptionalUserDto queryLocalUserByUserId(StringValue request) {
        var userApplicationStub = UserApplicationGrpc.newBlockingStub(getManagedChannel());
        return userApplicationStub.queryLocalUserByUserId(request);
    }

    @Override
    public UserDto queryUserById(Int64Value request) {
        var userApplicationStub = UserApplicationGrpc.newBlockingStub(getManagedChannel());
        return userApplicationStub.queryUserById(request);
    }

    @Override
    public PageUserDto pageQueryUser(PageQueryDto request) {
        var userApplicationStub = UserApplicationGrpc.newBlockingStub(getManagedChannel());
        return userApplicationStub.pageQueryUser(request);
    }

    @Override
    public BoolValue enableUser(UserDto request) {
        var userApplicationStub = UserApplicationGrpc.newBlockingStub(getManagedChannel());
        return userApplicationStub.enableUser(request);
    }

    @Override
    public BoolValue disableUser(UserDto request) {
        var userApplicationStub = UserApplicationGrpc.newBlockingStub(getManagedChannel());
        return userApplicationStub.disableUser(request);
    }

}
