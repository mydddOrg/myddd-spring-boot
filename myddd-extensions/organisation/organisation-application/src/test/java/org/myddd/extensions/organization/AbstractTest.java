package org.myddd.extensions.organization;

import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Random;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
public abstract class AbstractTest extends TestApplication {

    private static Random random = new Random();

    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    protected Long randomLong(){
        return random.nextLong();
    }

    protected OrganizationDto randomOrganizationWithUserId(Long userId){
        return OrganizationDto.newBuilder()
                .setName(randomId())
                .setDescription(randomId())
                .setCreateUserId(userId)
                .build();
    }

    protected OrganizationDto randomOrganization(){
        return OrganizationDto.newBuilder()
                .setName(randomId())
                .setDescription(randomId())
                .setCreateUserId(randomLong())
                .build();
    }

    protected OrganizationDto randomDepartment(OrganizationDto parent){
        return OrganizationDto.newBuilder()
                .setName(randomId())
                .setDescription(randomId())
                .setParentId(parent.getId())
                .setCreateUserId(randomLong())
                .build();
    }

    protected EmployeeDto randomEmployee(){
        return EmployeeDto.newBuilder()
                .setRelateUserId(randomId())
                .setEmail(randomId())
                .setPhone(randomId())
                .setUserId(randomLong())
                .setOrgId(1L)
                .setName(randomId()).build();
    }

    protected EmployeeDto randomEmployee(Long orgId){
        return EmployeeDto.newBuilder()
                .setEmail(randomId())
                .setPhone(randomId())
                .setRelateUserId(randomId())
                .setUserId(randomLong())
                .setOrgId(orgId)
                .setName(randomId()).build();
    }


    protected UserDto randomUserDto(){
        return UserDto.newBuilder()
                .setId(randomLong())
                .setUserId(randomId())
                .setPassword(randomId())
                .setName(randomId())
                .build();
    }

    protected OrgRoleGroupDto randomOrgRoleGroup(Long orgId){
        return OrgRoleGroupDto.newBuilder()
                .setName(randomId())
                .setOrganization(OrganizationDto.newBuilder().setId(orgId).build())
                .build();
    }

    protected OrgRoleDto randomOrgRoleDto(){
        return OrgRoleDto.newBuilder()
                .setName(randomId())
                .setCreator(randomLong())
                .build();
    }

    protected OrgRoleDto randomOrgRoleDto(long orgId,long roleGroupId){
        return OrgRoleDto.newBuilder()
                .setName(randomId())
                .setCreator(randomLong())
                .setOrganization(
                        OrganizationDto.newBuilder()
                                .setId(orgId)
                                .build()
                ).setRoleGroup(OrgRoleGroupDto.newBuilder().setId(roleGroupId).build())
                .build();
    }

    protected PermissionGroupDto randomPermissionGroupDto(){
        return PermissionGroupDto.newBuilder()
                .setRelateId(randomId())
                .setType(PermissionGroupTypeDto.PERMISSION_GROUP_TYPE_FORM)
                .build();
    }
}
