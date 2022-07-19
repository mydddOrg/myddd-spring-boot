package org.myddd.extensions.organisation;

import org.myddd.extensions.organisation.domain.EmployeeRoleAssignment;
import org.myddd.extensions.organisation.domain.OrgRole;
import org.myddd.extensions.organisation.domain.OrgRoleGroup;
import org.myddd.extensions.organisation.domain.OrgRoleRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class OrgRoleRepositoryJPA extends AbstractRepositoryJPA implements OrgRoleRepository {

    @Override
    public List<OrgRole> queryRolesByCompany(Long companyId) {
        return getEntityManager().createQuery("From OrgRole where company.id = :companyId", OrgRole.class)
                .setParameter("companyId",companyId)
                .getResultList();
    }

    @Override
    public EmployeeRoleAssignment queryEmployeeInRole(Long employeeId, Long roleId) {
        return getEntityManager().createQuery("from EmployeeRoleAssignment where employee.id = :employeeId and orgRole.id = :roleId",EmployeeRoleAssignment.class)
                .setParameter("employeeId",employeeId)
                .setParameter("roleId",roleId)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<OrgRole> queryEmployeeRoles(Long employeeId) {
        List<EmployeeRoleAssignment> roleAssignments = getEntityManager()
                .createQuery("from EmployeeRoleAssignment where employee.id = :employeeId",EmployeeRoleAssignment.class)
                .setParameter("employeeId",employeeId)
                .getResultList();

        return roleAssignments.stream().map(EmployeeRoleAssignment::getOrgRole).collect(Collectors.toList());
    }

    @Override
    public List<OrgRoleGroup> queryOrgRoleGroupByCompany(Long orgId) {
        return getEntityManager()
                .createQuery("from OrgRoleGroup where company.id = :orgId",OrgRoleGroup.class)
                .setParameter("orgId",orgId)
                .getResultList();
    }

    @Override
    public boolean isOrgRoleGroupEmpty(Long orgRoleGroupId) {
        var count = getEntityManager().createQuery("select count(id) as count from OrgRole where orgRoleGroup.id = :orgRoleGroupId",Long.class)
                .setParameter("orgRoleGroupId",orgRoleGroupId)
                .getSingleResult();
        return count == 0;
    }

    @Override
    public boolean isOrgRoleEmpty(Long orgRoleId) {
        var count = getEntityManager().createQuery("select count(id) as count from EmployeeRoleAssignment where orgRole.id = :orgRoleId",Long.class)
                .setParameter("orgRoleId",orgRoleId)
                .getSingleResult();
        return count == 0;
    }
}
