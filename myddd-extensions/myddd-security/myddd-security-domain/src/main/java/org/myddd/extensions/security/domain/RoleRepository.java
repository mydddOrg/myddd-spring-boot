package org.myddd.extensions.security.domain;

import org.myddd.domain.AbstractRepository;

public interface RoleRepository extends AbstractRepository {

    Role queryByRoleId(String roleId);

    boolean assignUserToRole(User user,Role role);

    boolean deAssignUserFromRole(Long userId,Long roleId);
}
