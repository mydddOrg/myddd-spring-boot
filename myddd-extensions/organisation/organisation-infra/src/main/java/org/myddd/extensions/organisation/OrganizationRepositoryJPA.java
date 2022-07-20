package org.myddd.extensions.organisation;

import org.myddd.extensions.organisation.domain.Employee;
import org.myddd.extensions.organisation.domain.Organization;
import org.myddd.extensions.organisation.domain.OrganizationRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class OrganizationRepositoryJPA extends AbstractRepositoryJPA implements OrganizationRepository {

    @Override
    public Organization queryOrganizationByOrgId(Long orgId) {
        return get(Organization.class,orgId);
    }

    @Override
    public List<Organization> queryTopCompaniesByUserId(Long userId) {
        List<Employee> employees = getEntityManager()
                .createQuery("from Employee where userId=:userId",Employee.class)
                .setParameter("userId",userId)
                .getResultList();
        var orgIdList = employees.stream().map(Employee::getOrgId).collect(Collectors.toList());
        return getEntityManager()
                .createQuery("from Organization where id in :ids",Organization.class)
                .setParameter("ids",orgIdList)
                .getResultList();
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        return save(organization);
    }

    @Override
    public Set<Organization> batchQueryOrganization(Set<Long> orgIds) {
        List<Organization> organizationList = getEntityManager()
                .createQuery("from Organization where id in :ids",Organization.class)
                .setParameter("ids",orgIds)
                .getResultList();
        return new HashSet<>(organizationList);
    }

    @Override
    public List<Organization> queryExistsOrganizationsByThirdSourceId(Long thirdSourceId) {
        return getEntityManager()
                .createQuery("from Organization where thirdSourceId = :thirdSourceId",Organization.class)
                .setParameter("thirdSourceId",thirdSourceId)
                .getResultList();
    }

    @Override
    public void batchDisableOrganizations(List<Organization> organizations) {
        organizations.forEach(it -> {
            it.setDisabled(true);
            getEntityManager().merge(it);
        });
    }

    @Override
    public void batchUpdateOrganizations(List<Organization> organizations) {
        organizations.forEach(it -> getEntityManager().merge(it));
    }

    @Override
    public boolean isOrganizationEmpty(Long id) {
        var count = getEntityManager().createQuery("select count(*) as count from EmployeeOrganizationRelation where organization.id = :orgId",Long.class)
                .setParameter("orgId",id)
                .getSingleResult();

        return count == 0;
    }

    @Override
    public Organization queryOrganizationByParentIdAndName(Long parentId, String name) {
        return getEntityManager().createQuery("from Organization where parent.id = :parentId and name = :name",Organization.class)
                .setParameter("parentId",parentId)
                .setParameter("name",name)
                .getResultList().stream().findFirst().orElse(null);
    }
}
