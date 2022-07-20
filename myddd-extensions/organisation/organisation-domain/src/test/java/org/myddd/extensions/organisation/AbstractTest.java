package org.myddd.extensions.organisation;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.IDGenerate;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.domain.*;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})

@SpringBootTest(classes = AbstractTest.class)
public abstract class AbstractTest{

    @Autowired
    protected ApplicationContext applicationContext;

    @Inject
    private IDGenerate idGenerate;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public static String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    protected Long randomLong(){
        return idGenerate.nextId();
    }

    protected Company randomCompany(){
        Company organization = new Company();
        organization.setName(randomId());
        organization.setCreator("lingen");
        organization.setCreateUserId(randomLong());
        return organization;
    }

    protected Company randomCompany(String userId){
        Company organization = new Company();
        organization.setName(randomId());
        organization.setCreator(userId);
        organization.setCreateUserId(randomLong());
        return organization;
    }

    protected Department randomDepartment(Company company){
        Department organization = new Department();
        organization.setParent(company);
        organization.setName(randomId());
        organization.setCreator("lingen");
        organization.setCreateUserId(randomLong());
        return organization;
    }

    protected Employee randomThirdSourceEmployee(){
        var employee = randomEmployee(randomLong());
        employee.setThirdSourceId(randomLong());
        employee.setDataSource(DataSource.OTHER);
        return employee;
    }

    protected Employee randomEmployee(){
        return randomEmployee(randomLong());
    }

    protected Employee randomEmployee(Long orgId){
        Employee employee = new Employee();
        employee.setEmployeeId(randomId());
        employee.setEmail(randomId());
        employee.setPhone(randomId());
        employee.setUserId(randomLong());
        employee.setRelateUserId(randomId());
        employee.setName(randomId());
        employee.setOrgId(orgId);
        return employee;
    }

    protected UserDto randomUserDto(){
        return UserDto.newBuilder()
                .setId(randomLong())
                .setUserId(randomId())
                .setPassword(randomId())
                .setName(randomId())
                .build();
    }

    protected OrgRole randomOrgRole(){
        OrgRole orgRole = new OrgRole();
        var company = randomCompany().createTopCompany();
        var orgRoleGroup = randomOrgRoleGroup(company).createRoleGroup();
        orgRole.setCompany(orgRoleGroup.getCompany());
        orgRole.setOrgRoleGroup(orgRoleGroup);
        orgRole.setName(randomId());
        orgRole.setCreator(randomLong());
        return orgRole;
    }


    protected OrgRole randomOrgRole(OrgRoleGroup orgRoleGroup){
        OrgRole orgRole = new OrgRole();
        orgRole.setCompany(orgRoleGroup.getCompany());
        orgRole.setOrgRoleGroup(orgRoleGroup);
        orgRole.setName(randomId());
        orgRole.setCreator(randomLong());
        return orgRole;
    }

    protected PermissionGroup randomPermissionGroup(){
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setRelateId(randomId());
        permissionGroup.setType(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM);
        return permissionGroup;
    }


    protected OrgRoleGroup randomOrgRoleGroup(Company company){
        var orgRoleGroup = new OrgRoleGroup();
        orgRoleGroup.setCompany(company);
        orgRoleGroup.setName(randomId());
        return orgRoleGroup;
    }

    protected UserDto userDtoFromEmployee(Employee employee){
        var builder = UserDto.newBuilder();
        builder.setName(employee.getName());
        if(Objects.nonNull(employee.getEmail()))builder.setEmail(employee.getEmail());
        if(Objects.nonNull(employee.getPhone()))builder.setPhone(employee.getPhone());
        builder.setId(employee.getId());
        builder.setUserId(String.valueOf(employee.getId()));

        return builder.build();
    }
}
