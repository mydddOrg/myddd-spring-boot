package org.myddd.extensions.organisation.domain;

import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "organization_",indexes = {
        @Index(name = "index_organization_third_source_id",columnList = "third_source_id_")
},
uniqueConstraints = {
        @UniqueConstraint(name = "unique_organization_third_id_and_third_source_id",columnNames = {"third_id_","third_source_id_"})
})
@DiscriminatorColumn(name = "category_", discriminatorType = DiscriminatorType.STRING)
public abstract class Organization extends BaseDistributedEntity {

    protected static final String PATH_SPLIT = "/";

    @Column(name = "third_id_")
    private String thirdId;

    @Column(name = "name_",nullable = false)
    private String name;

    @Column(name = "create_time_")
    private long createTime;

    @Column(name = "creator_",nullable = false)
    private String creator = "anonymous";

    @Column(name = "create_user_id_",nullable = false)
    private Long createUserId = -1L;

    @Column(name = "description_")
    private String description;

    @Column(name = "full_path_")
    private String fullPath;

    @Transient
    private String fullNamePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_")
    private Organization parent;

    @Column(name = "root_parent_id_")
    private Long rootParentId;

    @Column(name = "category_", insertable = false, updatable = false)
    private String category;

    @Column(name = "data_source_")
    private DataSource dataSource = DataSource.LOCAL;

    @Column(name = "third_source_id_")
    private Long thirdSourceId;

    private boolean disabled;

    @Transient
    private List<Employee> employeeList;


    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public void setFullNamePath(String fullNamePath) {
        this.fullNamePath = fullNamePath;
    }

    public String getFullNamePath() {
        return fullNamePath;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public void setRootParentId(Long rootParentId) {
        this.rootParentId = rootParentId;
    }

    public Long getRootParentId() {
        return rootParentId;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long getThirdSourceId() {
        return thirdSourceId;
    }

    public void setThirdSourceId(Long thirdSourceId) {
        this.thirdSourceId = thirdSourceId;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    private static OrganizationRepository repository;

    protected static OrganizationRepository getRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(OrganizationRepository.class);
        }
        return repository;
    }

    public static Organization queryOrganizationByOrgId(Long orgId){
        return getRepository().queryOrganizationByOrgId(orgId);
    }

    public boolean isCompany(){
        return this instanceof Company;
    }

    public Organization updateOrganization(){
        Organization exists = Organization.queryOrganizationByOrgId(this.getId());

        if(this.dataSource!=DataSource.LOCAL)throw new NotAllowedModifyThirdSourceException();
        if(Objects.isNull(exists))throw new OrganizationNotExistsException(getId());

        if(this.name.equals(getName()))return exists;

        var nameExists = isOrganizationNameExists(getParent().getId(),this.name);
        if(nameExists)throw new OrganizationNameExistsException(this.name);

        exists.setName(this.name);
        return getRepository().save(exists);
    }

    public static Set<Organization> batchQueryOrganizations(Set<Long> ids){
        return getRepository().batchQueryOrganization(ids);
    }

    public static void deleteOrganization(Long id){
        var exists = Organization.queryOrganizationByOrgId(id);
        if(exists.isCompany())throw new NotAllowedDeleteTopOrganizationException(id);

        var isEmpty = getRepository().isOrganizationEmpty(id);
        if(!isEmpty)throw new OrganizationEmployeeNotEmptyException(id);

        getRepository().remove(exists);
    }

    protected boolean isOrganizationNameExists(Long parentId,String name){
        var exists = getRepository().queryOrganizationByParentIdAndName(parentId,name);
        return Objects.nonNull(exists);
    }

    private List<String> fullPathList(){
        return Arrays.stream(fullPath.split("/")).filter(it -> !Strings.isNullOrEmpty(it)).collect(Collectors.toList());
    }



    /**
     * 查询组织的完整中文路径
     * @param organizations 需要获取完整中文组织路径的集合
     */
    public static void fetchOrganizationFullNamePath(List<Organization> organizations) {
        var fullPathList = organizations.stream().map(Organization::fullPathList).collect(Collectors.toList());
        var allDeptIdSet = fullPathList.stream().flatMap(List::stream).collect(Collectors.toSet());
        var allDeptOrganizations = Organization.batchQueryOrganizations(allDeptIdSet.stream().map(Long::parseLong).collect(Collectors.toSet()));
        var allDeptOrganizationMap = allDeptOrganizations.stream().collect(Collectors.toMap(Organization::getId,Organization::getName));
        organizations.forEach(it -> {
            var idFullPathList = it.fullPathList();
            var orgFullNamePath = new StringBuilder();
            idFullPathList.forEach(stringId -> {
                orgFullNamePath.append("/");
                orgFullNamePath.append(allDeptOrganizationMap.get(Long.parseLong(stringId)));
            });
            orgFullNamePath.append("/");
            orgFullNamePath.append(it.getName());

            it.setFullNamePath(orgFullNamePath.toString());
        });
    }
}
