package org.myddd.extensions.organisation;

import org.myddd.extensions.organisation.domain.*;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class EmployeeRepositoryJPA extends AbstractRepositoryJPA implements EmployeeRepository {

    public static final String EMPLOYEE_ID = "employeeId";
    public static final String EMPLOYEE_IDS = "employeeIds";
    public static final String ORG_ID = "orgId";

    @Override
    public boolean isEmployeeIdExists(Long orgId, String employeeId) {
        var exists = getEntityManager().createQuery("from Employee where orgId = :orgId and employeeId = :employeeId",Employee.class)
                .setParameter(ORG_ID,orgId)
                .setParameter(EMPLOYEE_ID,employeeId)
                .getResultList().stream().findFirst().orElse(null);
        return Objects.nonNull(exists);
    }

    @Override
    public Employee queryEmployeeByUserIdAndOrgId(Long userId, long orgId) {
        return getEntityManager().createQuery("from Employee where userId = :userId and orgId = :orgId",Employee.class)
                .setParameter("userId",userId)
                .setParameter(ORG_ID,orgId)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<EmployeeOrganizationRelation> queryEmployeeOrganizations(Long employeeId) {
        return getEntityManager().createQuery("from EmployeeOrganizationRelation where employee.id = :employeeId",EmployeeOrganizationRelation.class)
                .setParameter(EMPLOYEE_ID,employeeId)
                .getResultList();
    }

    @Override
    public EmployeeOrganizationRelation queryEmployeeInOrganization(Long employeeId, Long organizationId) {
        return getEntityManager().createQuery("from EmployeeOrganizationRelation where employee.id = :employeeId and organization.id = :organizationId",EmployeeOrganizationRelation.class)
                .setParameter(EMPLOYEE_ID,employeeId)
                .setParameter("organizationId",organizationId)
                .getResultList().stream().findFirst().orElse(null);
    }

    public List<Employee> queryExistsEmployeeByThirdSourceId(Long thirdSourceId){
        return getEntityManager()
                .createQuery("from Employee where thirdSourceId = :thirdSourceId",Employee.class)
                .setParameter("thirdSourceId",thirdSourceId)
                .getResultList();
    }

    @Override
    public List<EmployeeOrganizationRelation> queryExistsEmployeeOrganizationRelationByThirdSourceId(Long thirdSourceId) {
        return getEntityManager()
                .createQuery("from EmployeeOrganizationRelation where thirdSourceId = :thirdSourceId",EmployeeOrganizationRelation.class)
                .setParameter("thirdSourceId",thirdSourceId)
                .getResultList();
    }

    @Override
    public void batchDisableEmployees(List<Employee> employees) {
        employees.forEach(it -> {
            it.setDisabled(true);
            getEntityManager().merge(it);
        });
    }

    @Override
    public void batchUpdateEmployees(List<Employee> employees) {
        employees.forEach(it -> getEntityManager().merge(it));
    }

    @Override
    public Employee queryEmployeeByType(Long orgId, EmployeeType employeeType) {
        return getEntityManager()
                .createQuery("from Employee where employeeType = :employeeType and orgId = : orgId",Employee.class)
                .setParameter("employeeType",employeeType)
                .setParameter(ORG_ID,orgId)
                .getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Map<Long, List<OrgRole>> batchQueryEmployeeRoles(List<Long> employeeIds) {
        var roleAssignments = getEntityManager().createQuery("from EmployeeRoleAssignment where employee.id in :employeeIds",EmployeeRoleAssignment.class)
                .setParameter(EMPLOYEE_IDS,employeeIds)
                .getResultList();

        return roleAssignments.stream().collect(Collectors.groupingBy(it -> it.getEmployee().getId(), Collectors.mapping(EmployeeRoleAssignment::getOrgRole,Collectors.toList())));
    }

    @Override
    public Map<Long, List<Organization>> batchQueryEmployeeOrganizations(List<Long> employeeIds) {
        var employeeOrganizations = getEntityManager().createQuery("from EmployeeOrganizationRelation where employee.id in :employeeIds",EmployeeOrganizationRelation.class)
                .setParameter(EMPLOYEE_IDS,employeeIds)
                .getResultList();

        return employeeOrganizations.stream().collect(Collectors.groupingBy(it -> it.getEmployee().getId(), Collectors.mapping(EmployeeOrganizationRelation::getOrganization,Collectors.toList())));
    }

    @Override
    public Employee queryOrgEmployeeByUserId(Long orgId, Long userId) {
        return getEntityManager().createQuery("from Employee where orgId = :orgId and userId = :userId",Employee.class)
                .setParameter(ORG_ID,orgId)
                .setParameter("userId",userId)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Employee queryOrgEmployeeByEmail(Long orgId, String email) {
        return getEntityManager().createQuery("from Employee where orgId = :orgId and email = :email",Employee.class)
                .setParameter(ORG_ID,orgId)
                .setParameter("email",email)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Employee queryOrgEmployeeByPhone(Long orgId, String phone) {
        return getEntityManager().createQuery("from Employee where orgId = :orgId and phone = :phone",Employee.class)
                .setParameter(ORG_ID,orgId)
                .setParameter("phone",phone)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void batchClearEmployeeOrganizations(List<Long> idList) {
        getEntityManager().createQuery("delete from EmployeeOrganizationRelation where employee.id in :employeeIds")
                .setParameter(EMPLOYEE_IDS,idList)
                .executeUpdate();
    }

}
