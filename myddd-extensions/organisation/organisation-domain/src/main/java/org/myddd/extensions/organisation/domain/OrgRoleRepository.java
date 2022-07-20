package org.myddd.extensions.organisation.domain;

import org.myddd.domain.AbstractRepository;

import java.util.List;

public interface OrgRoleRepository extends AbstractRepository {

    List<OrgRole> queryRolesByCompany(Long companyId);

    EmployeeRoleAssignment queryEmployeeInRole(Long employeeId,Long roleId);

    List<OrgRole> queryEmployeeRoles(Long employeeId);

    List<OrgRoleGroup> queryOrgRoleGroupByCompany(Long orgId);

    boolean isOrgRoleGroupEmpty(Long orgRoleGroupId);

    boolean isOrgRoleEmpty(Long orgRoleId);
}
