package org.myddd.extensions.organisation;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.IDGenerate;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.domain.*;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.myddd.test.TestApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
public abstract class AbstractTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @Inject
    private IDGenerate idGenerate;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String randomId(){
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

    protected Department randomDepartment(Company company){
        var organization = new Department();
        organization.setParent(company);
        organization.setName(randomId());
        organization.setCreator("lingen");
        organization.setCreateUserId(randomLong());
        return organization;
    }

    protected Employee randomEmployee(){
        return randomEmployee(randomLong());
    }

    protected Employee randomEmployee(Long orgId){
        Employee employee = new Employee();
        employee.setEmployeeId(randomId());
        employee.setRelateUserId(randomId());
        employee.setUserId(randomLong());
        employee.setName(randomId());
        employee.setOrgId(orgId);
        return employee;
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
}
