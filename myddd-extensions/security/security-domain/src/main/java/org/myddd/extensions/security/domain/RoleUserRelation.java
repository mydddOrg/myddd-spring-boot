package org.myddd.extensions.security.domain;

import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.RoleNotFoundException;
import org.myddd.extensions.security.UserNotFoundException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "role_user_",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_role_user",columnNames = {"role_id_","user_id_"})
        }
)
public class RoleUserRelation extends BaseDistributedEntity {

    @ManyToOne
    @JoinColumn(name = "user_id_")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id_")
    private Role role;

    private long created;

    public RoleUserRelation(User user,Role role){
        this.user = user;
        this.role = role;
        this.created = System.currentTimeMillis();
    }


    public long getCreated() {
        return created;
    }

    private static RoleRepository roleRepository;

    private static RoleRepository getRoleRepository(){
        if(Objects.isNull(roleRepository)){
            roleRepository = InstanceFactory.getInstance(RoleRepository.class);
        }
        return roleRepository;
    }

    public static boolean assignUserToRole(Long userId,Long roleId){
        User existsUser = getRoleRepository().get(User.class,userId);
        if(Objects.isNull(existsUser)){
            throw new UserNotFoundException(userId);
        }

        Role existRole = getRoleRepository().get(Role.class,roleId);
        if(Objects.isNull(existRole)){
            throw new RoleNotFoundException();
        }

        return getRoleRepository().assignUserToRole(existsUser,existRole);
    }

    public static boolean deAssignUserFromRole(Long userId,Long roleId){
        User existsUser = getRoleRepository().get(User.class,userId);
        if(Objects.isNull(existsUser)){
            throw new UserNotFoundException(userId);
        }

        Role existRole = getRoleRepository().get(Role.class,roleId);
        if(Objects.isNull(existRole)){
            throw new RoleNotFoundException();
        }

        return getRoleRepository().deAssignUserFromRole(existsUser.getId(),existRole.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleUserRelation)) return false;
        RoleUserRelation that = (RoleUserRelation) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, role);
    }

}
