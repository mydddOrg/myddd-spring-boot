package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.OrgRoleGroupNotEmptyException;
import org.myddd.extensions.organisation.OrgRoleGroupNotExistsException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "org_role_group_")
public class OrgRoleGroup extends BaseDistributedEntity {

    @ManyToOne
    @JoinColumn(name = "org_id_",nullable = false)
    private Company company;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_")
    private long created;

    public OrgRoleGroup(){
        this.created = System.currentTimeMillis();
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

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

    private static OrgRoleRepository repository;

    private static OrgRoleRepository getRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(OrgRoleRepository.class);
        }
        return repository;
    }

    public OrgRoleGroup createRoleGroup(){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name),"角色组名称不能为空");
        this.created = System.currentTimeMillis();
        return getRepository().save(this);
    }

    public static OrgRoleGroup queryOrgRoleGroup(Long id){
        return getRepository().get(OrgRoleGroup.class,id);
    }

    public static List<OrgRoleGroup> queryByCompany(Long id){
        return getRepository().queryOrgRoleGroupByCompany(id);
    }

    public static void removeOrgRoleGroup(Long id){
        var exists = getRepository().get(OrgRoleGroup.class,id);
        if(Objects.isNull(exists)) throw new OrgRoleGroupNotExistsException(id);

        var isEmpty = getRepository().isOrgRoleGroupEmpty(id);
        if(!isEmpty)throw new OrgRoleGroupNotEmptyException(id);

        getRepository().remove(exists);
    }

    public OrgRoleGroup update(){
        var exists = getRepository().get(OrgRoleGroup.class,getId());
        if(Objects.isNull(exists)) throw new OrgRoleGroupNotExistsException(getId());

        exists.setName(getName());
        return getRepository().save(exists);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrgRoleGroup)) return false;
        if (!super.equals(o)) return false;
        OrgRoleGroup that = (OrgRoleGroup) o;
        return Objects.equals(company, that.company) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company, name);
    }
}
