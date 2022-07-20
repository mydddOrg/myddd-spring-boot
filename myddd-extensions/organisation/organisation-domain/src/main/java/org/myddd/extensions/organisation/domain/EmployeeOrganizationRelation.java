package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.EmployeeNotExistsException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "employee_organization_",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"organization_","employee_"})
        })
public class EmployeeOrganizationRelation extends BaseDistributedEntity {

    protected static final String PATH_SPLIT = "/";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_")
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_")
    private Employee employee;

    private String path;

    @Column(name = "data_source_")
    private DataSource dataSource = DataSource.LOCAL;

    @Column(name = "third_source_id_")
    private Long thirdSourceId;

    public EmployeeOrganizationRelation(){

    }

    public EmployeeOrganizationRelation(Organization organization,Employee employee){
        this.organization = organization;
        this.employee = employee;
    }

    public EmployeeOrganizationRelation(Organization organization,Employee employee,DataSource dataSource,Long thirdSourceId){
        this(organization,employee);
        this.dataSource = dataSource;
        this.thirdSourceId = thirdSourceId;
    }



    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Long getThirdSourceId() {
        return thirdSourceId;
    }

    public void setThirdSourceId(Long thirdSourceId) {
        this.thirdSourceId = thirdSourceId;
    }

    public String matchKey(){
        return organization.getId() + "-" + employee.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeOrganizationRelation)) return false;
        if (!super.equals(o)) return false;
        EmployeeOrganizationRelation that = (EmployeeOrganizationRelation) o;
        return organization.equals(that.organization) && employee.equals(that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), organization, employee);
    }

    private static EmployeeRepository entityRepository;

    private static EmployeeRepository getEntityRepository(){
        if(Objects.isNull(entityRepository)){
            entityRepository = InstanceFactory.getInstance(EmployeeRepository.class);
        }
        return entityRepository;
    }

    public static EmployeeOrganizationRelation assignEmployeeToOrganization(Employee employee, Organization organization){
        Employee.validThirdSourcePermission(employee.getId());

        EmployeeOrganizationRelation employeeOrganizationRelation = new EmployeeOrganizationRelation();
        employeeOrganizationRelation.setEmployee(employee);
        employeeOrganizationRelation.setOrganization(organization);
        employeeOrganizationRelation.setPath(organization.getFullPath() + PATH_SPLIT  + organization.getId());
        return getEntityRepository().save(employeeOrganizationRelation);
    }

    public static EmployeeOrganizationRelation assignEmployeeToOrganization(Long employeeId, Long orgId){
        var organization = Organization.queryOrganizationByOrgId(orgId);
        if(Objects.isNull(organization))throw new OrganizationNotExistsException(orgId);

        var employee = Employee.queryEmployeeById(employeeId);
        if(Objects.isNull(employee))throw new EmployeeNotExistsException(employeeId);

        return assignEmployeeToOrganization(employee, organization);
    }

    public static void reAssignEmployeeListToOrganization(List<Long> employeeIds,List<Long> orgIds){
        Preconditions.checkArgument(!employeeIds.isEmpty(),"雇员不能为空");
        Preconditions.checkArgument(!orgIds.isEmpty(),"组织不能为空");
        getEntityRepository().batchClearEmployeeOrganizations(employeeIds);
        employeeIds.forEach(employeeId -> orgIds.forEach(orgId -> assignEmployeeToOrganization(employeeId,orgId)));
    }

    public static List<Organization> queryEmployeeOrganizations(Long employeeId){
        List<EmployeeOrganizationRelation> relations = getEntityRepository().queryEmployeeOrganizations(employeeId);
        return relations.stream().map(EmployeeOrganizationRelation::getOrganization).collect(Collectors.toList());
    }


    public static Map<Long,List<Organization>> batchQueryEmployeeOrganizations(List<Long> ids){
        return getEntityRepository().batchQueryEmployeeOrganizations(ids);
    }


    public static boolean deAssignEmployeeFromOrganization(Long employeeId, Long organizationId){

        EmployeeOrganizationRelation relation = getEntityRepository().queryEmployeeInOrganization(employeeId,organizationId);
        if(Objects.nonNull(relation)){
            Employee.validThirdSourcePermission(employeeId);
            getEntityRepository().remove(relation);
            return true;
        }
        return false;
    }



    /**
     * 查询一个雇员所属的所有组织，包括直属以及直属组织的上级组织，以此类推
     * @param employeeId 雇员ID
     * @return 所有组织信息
     */
    public static List<Organization> queryEmployeeAllOrganizations(Long employeeId){
        //直属组织
        Set<Organization> organizationSet = new HashSet<>(queryEmployeeOrganizations(employeeId));

        Set<Long> orgIds = new HashSet<>();
        organizationSet.forEach(organization -> {
            String path = organization.getFullPath();
            orgIds.addAll(Arrays.stream(path.split(PATH_SPLIT)).filter(it -> !it.isBlank()).map(Long::parseLong).collect(Collectors.toSet()));
        });

        //关联所有组织
        Set<Organization> relateOrganizations = Organization.batchQueryOrganizations(orgIds);
        organizationSet.addAll(relateOrganizations);
        return new ArrayList<>(organizationSet);
    }
}
