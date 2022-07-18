package org.myddd.extensions.security.application;



import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.api.UserRoleApplication;
import org.myddd.extensions.security.application.assembler.RoleAssembler;
import org.myddd.extensions.security.domain.Role;

import javax.transaction.Transactional;

public class UserRoleApplicationImpl implements UserRoleApplication {

    @Override
    @Transactional
    public RoleDto createRole(RoleDto request) {
        Role createdRole = RoleAssembler.toEntity(request).createRole();
        return RoleAssembler.toDto(createdRole);
    }

}
