package org.myddd.extensions.organisation.domain;

import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.EmployeeNotExistsException;
import org.myddd.extensions.organisation.EmployeeNotInCompanyException;
import org.myddd.extensions.organisation.EmployeeNotInRoleException;
import org.myddd.extensions.organisation.OrgRoleNotExistsException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee_role_",uniqueConstraints = {
        @UniqueConstraint(name = "unique_EmployeeRoleAssignment_employee_orgRole",columnNames = {"employee_id_","role_id_"})
})
public class EmployeeRoleAssignment extends BaseDistributedEntity {

    @ManyToOne
    @JoinColumn(name = "employee_id_")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_id_")
    private OrgRole orgRole;

    private long created;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public OrgRole getOrgRole() {
        return orgRole;
    }

    public void setOrgRole(OrgRole orgRole) {
        this.orgRole = orgRole;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeRoleAssignment)) return false;
        if (!super.equals(o)) return false;
        EmployeeRoleAssignment that = (EmployeeRoleAssignment) o;
        return employee.equals(that.employee) && orgRole.equals(that.orgRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employee, orgRole);
    }

    private static OrgRoleRepository roleRepository;

    private static OrgRoleRepository getRoleRepository(){
        if(Objects.isNull(roleRepository)){
            roleRepository = InstanceFactory.getInstance(OrgRoleRepository.class);
        }
        return roleRepository;
    }

    public static void assignEmployeeToRole(Long employeeId,Long roleId){
        var exists = getRoleRepository().queryEmployeeInRole(employeeId,roleId);
        if(Objects.nonNull(exists))return;
        Employee employee = Employee.queryEmployeeById(employeeId);
        if(Objects.isNull(employee)){
            throw new EmployeeNotExistsException(employeeId);
        }

        OrgRole orgRole = OrgRole.queryById(roleId);
        if(Objects.isNull(orgRole)){
            throw new OrgRoleNotExistsException(roleId);
        }

        if(orgRole.getCompany().getId() != employee.getOrgId()){
            throw new EmployeeNotInCompanyException();
        }

        EmployeeRoleAssignment employeeRoleAssignment = new EmployeeRoleAssignment();
        employeeRoleAssignment.setEmployee(employee);
        employeeRoleAssignment.setOrgRole(orgRole);
        employeeRoleAssignment.setCreated(System.currentTimeMillis());
        getRoleRepository().save(employeeRoleAssignment);
    }


    public static boolean deAssignEmployeeFromRole(Long employeeId,Long roleId){
        EmployeeRoleAssignment employeeRoleAssignment = getRoleRepository().queryEmployeeInRole(employeeId,roleId);
        if(Objects.isNull(employeeRoleAssignment)){
            throw new EmployeeNotInRoleException();
        }
        getRoleRepository().remove(employeeRoleAssignment);
        return true;
    }

    public static List<OrgRole> queryEmployeeRoles(Long employeeId){
        return getRoleRepository().queryEmployeeRoles(employeeId);
    }

}
