package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.OrgRoleDto;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgRoleVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String name;

    private OrganizationVO company;

    private OrgRoleGroupVO roleGroup;

    private Long creator;

    private long created;

    public OrgRoleVO(){}

    public OrgRoleVO(long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganizationVO getCompany() {
        return company;
    }

    public void setCompany(OrganizationVO company) {
        this.company = company;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreated() {
        return created;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getCreator() {
        return creator;
    }

    public OrgRoleGroupVO getRoleGroup() {
        return roleGroup;
    }

    public void setRoleGroup(OrgRoleGroupVO roleGroup) {
        this.roleGroup = roleGroup;
    }

    public OrgRoleDto toDto(){
        OrgRoleDto.Builder builder = OrgRoleDto.newBuilder();
        if(Objects.nonNull(id) && id > 0) builder.setId(id);
        if(Objects.nonNull(name))builder.setName(name);
        if(Objects.nonNull(company))builder.setOrganization(company.toDTO());
        if(Objects.nonNull(roleGroup))builder.setRoleGroup(roleGroup.toDto());
        builder.setCreated(created);
        if(Objects.nonNull(creator))builder.setCreator(creator);
        return builder.build();
    }

    public static OrgRoleVO of(OrgRoleDto orgRoleDto){
        OrgRoleVO orgRoleVO = new OrgRoleVO();
        orgRoleVO.setId(orgRoleDto.getId());
        orgRoleVO.setName(orgRoleDto.getName());
        orgRoleVO.setCompany(OrganizationVO.of(orgRoleDto.getOrganization()));
        orgRoleVO.setRoleGroup(OrgRoleGroupVO.of(orgRoleDto.getRoleGroup()));
        return orgRoleVO;
    }


}
