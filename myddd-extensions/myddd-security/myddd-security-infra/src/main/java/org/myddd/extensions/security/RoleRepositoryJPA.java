package org.myddd.extensions.security;

import org.myddd.extensions.security.domain.Role;
import org.myddd.extensions.security.domain.RoleRepository;
import org.myddd.extensions.security.domain.RoleUserRelation;
import org.myddd.extensions.security.domain.User;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.Objects;

@Named
public class RoleRepositoryJPA extends AbstractRepositoryJPA implements RoleRepository {
    @Override
    public Role queryByRoleId(String roleId) {
        return getEntityManager().createQuery("from Role where roleId = :roleId",Role.class)
                .setParameter("roleId",roleId)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public boolean assignUserToRole(User user, Role role) {
        save(new RoleUserRelation(user,role));
        return true;
    }

    @Override
    public boolean deAssignUserFromRole(Long userId,Long roleId) {
        RoleUserRelation roleUserRelation = getEntityManager()
                .createQuery("from RoleUserRelation where role.id= :roleId and user.id = :userId ",RoleUserRelation.class)
                .setParameter("userId",userId)
                .setParameter("roleId",roleId)
                .getResultList().stream().findFirst().orElse(null);
        if(Objects.nonNull(roleUserRelation)){
            remove(roleUserRelation);
        }
        return true;
    }
}
