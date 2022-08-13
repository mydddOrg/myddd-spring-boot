package org.myddd.extensions.security;


import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.api.UserRoleApplication;
import org.myddd.extensions.security.api.UserRoleApplicationGrpc;

import javax.inject.Named;

@Named
public class UserRoleApplicationGrpcBridge extends AbstractSecurityGrpcBridge implements UserRoleApplication {

    @Override
    public RoleDto createRole(RoleDto request) {
        var userRoleApplicationStub = UserRoleApplicationGrpc.newBlockingStub(getManagedChannel());
        return userRoleApplicationStub.createRole(request);
    }
}
