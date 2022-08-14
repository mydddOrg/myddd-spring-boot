package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.OrgRoleGroupDto;
import org.myddd.extensions.organization.api.OrganizationDto;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgRoleGroupVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String name;

    private OrganizationVO company;

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

    public OrgRoleGroupDto toDto(){
        var builder = OrgRoleGroupDto.newBuilder();
        if(id > 0)builder.setId(id);
        if(Objects.nonNull(name))builder.setName(name);
        if(Objects.nonNull(company))builder.setOrganization(OrganizationDto.newBuilder().setId(company.getId()).build());
        return builder.build();
    }

    public static OrgRoleGroupVO of(OrgRoleGroupDto dto){
        var roleGroupVO = new OrgRoleGroupVO();
        roleGroupVO.setId(dto.getId());
        roleGroupVO.setName(dto.getName());
        roleGroupVO.setCompany(OrganizationVO.of(dto.getOrganization()));
        return roleGroupVO;
    }
}
