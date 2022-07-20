package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.OnlyAllowedCompanyException;
import org.myddd.extensions.organisation.OrgRoleGroupNotExistsException;
import org.myddd.extensions.organisation.OrgRoleNotExistsException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * 组织角色是雇员在组织中的角色
 */
@Entity
@Table(name = "org_role_")
public class OrgRole extends BaseDistributedEntity {

    @Column(name = "name_",nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "org_id_",nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id",nullable = false)
    private OrgRoleGroup orgRoleGroup;

    @Column(name = "creator_",nullable = false)
    private Long creator;

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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setOrgRoleGroup(OrgRoleGroup orgRoleGroup) {
        this.orgRoleGroup = orgRoleGroup;
    }

    public OrgRoleGroup getOrgRoleGroup() {
        return orgRoleGroup;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private static OrgRoleRepository repository;

    private static OrgRoleRepository getRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(OrgRoleRepository.class);
        }
        return repository;
    }

    public OrgRole createRole(){
        Preconditions.checkNotNull(company,"组织角色必须指定关联公司");
        Preconditions.checkNotNull(orgRoleGroup,"组织角色必须指定角色组");
        Organization organization = Organization.queryOrganizationByOrgId(company.getId());
        if(Objects.isNull(organization)){
            throw new OrganizationNotExistsException(company.getId());
        }
        if(!organization.isCompany()){
            throw new OnlyAllowedCompanyException();
        }
        this.company = (Company) organization;

        OrgRoleGroup queryOrgRoleGroup = OrgRoleGroup.queryOrgRoleGroup(this.orgRoleGroup.getId());
        if(Objects.isNull(queryOrgRoleGroup))throw new OrgRoleGroupNotExistsException(this.orgRoleGroup.getId());
        this.orgRoleGroup = queryOrgRoleGroup;

        this.created = System.currentTimeMillis();
        return getRepository().save(this);
    }

    public OrgRole updateRole(){
        OrgRole exists = OrgRole.queryById(getId());
        if(Objects.isNull(exists)){
            throw new OrgRoleNotExistsException(getId());
        }

        exists.setName(this.name);
        exists.setUpdated(System.currentTimeMillis());
        return getRepository().save(exists);
    }

    public static List<OrgRole> queryRolesByCompany(Long companyId){
        Preconditions.checkArgument(companyId > 0,"公司ID参数错误,不能小于或等于0");
        return getRepository().queryRolesByCompany(companyId);
    }

    public static OrgRole queryById(Long id){
        return getRepository().get(OrgRole.class,id);
    }

    public static void removeRole(Long id){
        var exists = getRepository().get(OrgRole.class,id);
        if(Objects.isNull(exists))throw new OrgRoleNotExistsException(id);

        var orgRoleEmpty = getRepository().isOrgRoleEmpty(id);
        if(!orgRoleEmpty)throw new OrgRoleNotEmptyException();

        getRepository().remove(exists);
    }

    public static void changeOrgRoleGroup(Long id,Long roleGroupId){
        var exists = getRepository().get(OrgRole.class,id);
        if(Objects.isNull(exists))throw new OrgRoleNotExistsException(id);

        var roleGroup = OrgRoleGroup.queryOrgRoleGroup(roleGroupId);
        if(Objects.isNull(roleGroup))throw new OrgRoleGroupNotExistsException(roleGroupId);

        exists.orgRoleGroup = roleGroup;
        getRepository().save(exists);
    }
}
