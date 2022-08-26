package org.myddd.extensions.organization.application.assembler;

import org.myddd.extensions.organisation.domain.Company;
import org.myddd.extensions.organisation.domain.Department;
import org.myddd.extensions.organisation.domain.Organization;
import org.myddd.extensions.organization.api.OrganizationDto;

import java.util.Objects;

public class OrganizationAssembler {

    private OrganizationAssembler(){}

    public static OrganizationDto toDTO(Organization organization){
        if(Objects.isNull(organization))return null;

        var builder =  OrganizationDto.newBuilder()
                .setId(organization.getId())
                .setName(organization.getName())
                .setCreateTime(organization.getCreateTime())
                .setCreateUserId(organization.getCreateUserId())
                .setCategory(organization.isCompany()? OrganizationDto.Category.COMPANY: OrganizationDto.Category.DEPARTMENT);
        if(Objects.nonNull(organization.getDescription()))builder.setDescription(organization.getDescription());
        if(Objects.nonNull(organization.getParent()))builder.setParentId(organization.getParent().getId());
        if(Objects.nonNull(organization.getFullPath()))builder.setPath(organization.getFullPath());
        if(Objects.nonNull(organization.getFullNamePath()))builder.setFullNamePath(organization.getFullNamePath());
        if(Objects.nonNull(organization.getDataSource()))builder.setDataSource(organization.getDataSource().toString());
        return builder.build();
    }

    public static Company toCompany(OrganizationDto organizationDto){
        Company company = new Company();
        if(organizationDto.getId() > 0){
            company.setId(organizationDto.getId());
        }
        company.setName(organizationDto.getName());
        company.setCreateTime(organizationDto.getCreateTime());
        company.setDescription(organizationDto.getDescription());
        company.setCreateUserId(organizationDto.getCreateUserId());
        return company;
    }

    public static Department toDepartment(OrganizationDto organizationDto){
        Department department = new Department();
        if(organizationDto.getId() > 0){
            department.setId(organizationDto.getId());
        }
        department.setName(organizationDto.getName());
        department.setCreateTime(organizationDto.getCreateTime());
        department.setDescription(organizationDto.getDescription());
        department.setCreateUserId(organizationDto.getCreateUserId());
        return department;
    }
}
