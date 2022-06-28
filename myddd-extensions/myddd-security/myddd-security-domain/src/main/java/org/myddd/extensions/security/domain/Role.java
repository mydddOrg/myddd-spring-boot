package org.myddd.extensions.security.domain;

import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.RoleIdEmptyException;
import org.myddd.extensions.security.RoleNameEmptyException;
import org.myddd.extensions.security.RoleNotFoundException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_",
        indexes = {
                @Index(name = "index_role_id", columnList = "role_id_")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_role_id",columnNames = {"role_id_"})
        }
)
public class Role extends BaseDistributedEntity {

    @Column(name = "name_",nullable = false, length = 100)
    private String name;

    @Column(name = "role_id_", nullable = false)
    private String roleId;

    @Column(name = "created_")
    private long created;

    @Column(name = "updated_")
    private long updated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    private static RoleRepository roleRepository;

    private static RoleRepository getRoleRepository() {
        if (Objects.isNull(roleRepository)) {
            roleRepository = InstanceFactory.getInstance(RoleRepository.class);
        }
        return roleRepository;
    }

    public Role createRole() {
        if (Strings.isNullOrEmpty(roleId)) throw new RoleIdEmptyException();
        if (Strings.isNullOrEmpty(name)) throw new RoleNameEmptyException();

        this.created = System.currentTimeMillis();
        return getRoleRepository().save(this);
    }

    public static Role queryByRoleId(String roleId){
        return getRoleRepository().queryByRoleId(roleId);
    }

    public Role updateRole(){
        Role exists = queryByRoleId(roleId);
        if(Objects.isNull(exists)){
            throw new RoleNotFoundException();
        }
        exists.setName(name);
        return getRoleRepository().save(exists);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roleId);
    }
}
