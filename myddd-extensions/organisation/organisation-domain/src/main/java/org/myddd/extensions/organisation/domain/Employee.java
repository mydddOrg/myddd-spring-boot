package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.organisation.*;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.lang.BadParameterException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee_",uniqueConstraints = {
        @UniqueConstraint(name = "unique_employee_relateUserId_thirdSourceId",columnNames = {"relate_user_id_","third_source_id_"}),
        @UniqueConstraint(name = "unique_employee_orgId_employeeId",columnNames = {"org_id_","employee_id_"})
})
public class Employee extends BaseDistributedEntity {


    @Column(name = "relate_user_id_",nullable = false)
    private String relateUserId;

    @Column(name = "user_id_")
    private Long userId;

    @Column(name = "employee_id_")
    private String employeeId;

    @Column(name = "employee_type_")
    private EmployeeType employeeType = EmployeeType.EMPLOYEE_USER;

    @Column(name = "org_id_",nullable = false)
    private long orgId;

    @Column(name = "name_",nullable = false)
    private String name;

    @Column(name = "create_time_")
    private long createTime;

    @Column(name = "phone_")
    private String phone;

    @Column(name = "email_")
    private String email;

    @Column(name = "updated_")
    private long updated;

    @Column(name = "created_")
    private long created;

    @Column(name = "data_source_")
    private DataSource dataSource = DataSource.LOCAL;

    @Column(name = "third_source_id_")
    private Long thirdSourceId;

    private boolean disabled;

    @Transient
    private String orgFullPath;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRelateUserId() {
        return relateUserId;
    }

    public void setRelateUserId(String relateUserId) {
        this.relateUserId = relateUserId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
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

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setOrgFullPath(String orgFullPath) {
        this.orgFullPath = orgFullPath;
    }

    public String getOrgFullPath() {
        return orgFullPath;
    }

    public boolean isDisabled() {
        return disabled;
    }

    private static OrganizationRepository organizationRepository;

    private static OrganizationRepository getOrganizationRepository(){
        if(Objects.isNull(organizationRepository)){
            organizationRepository = InstanceFactory.getInstance(OrganizationRepository.class);
        }
        return organizationRepository;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return employeeId.equals(employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employeeId);
    }

    private static EmployeeRepository repository;

    private static EmployeeRepository getRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(EmployeeRepository.class);
        }
        return repository;
    }

    private static UserApplication userApplication;

    private static UserApplication getUserApplication(){
        if(Objects.isNull(userApplication)){
            userApplication = InstanceFactory.getInstance(UserApplication.class);
        }
        return userApplication;
    }

    private static EmployeeIdGenerator employeeIdGenerator;

    private static EmployeeIdGenerator getEmployeeIdGenerator(){
        if(Objects.isNull(employeeIdGenerator)){
            employeeIdGenerator = InstanceFactory.getInstance(EmployeeIdGenerator.class);
        }
        return employeeIdGenerator;
    }

    public Employee createEmployee(){
        if(Strings.isNullOrEmpty(name)) throw new BadParameterException();
        setCreateTime(System.currentTimeMillis());
        return getRepository().save(this);
    }

    public static Employee queryEmployeeById(long id){
        return getRepository().get(Employee.class,id);
    }

    public static Employee queryEmployeeByUserIdAndOrgId(Long userId,long orgId){
        return getRepository().queryEmployeeByUserIdAndOrgId(userId,orgId);
    }

    public Employee updateEmployee(){
        if(isThirdSource())throw new NotAllowedModifyThirdSourceException();
        var exists = Employee.queryEmployeeById(getId());
        if(Objects.isNull(exists))throw new EmployeeNotExistsException(getId());

        exists.setName(name);
        exists.setUpdated(System.currentTimeMillis());
        return getRepository().save(exists);
    }

    public UserDto toUserDto(){
        var builder = UserDto.newBuilder();
        builder.setUserId(relateUserId);
        if(!Strings.isNullOrEmpty(email))builder.setEmail(email);
        if(!Strings.isNullOrEmpty(phone))builder.setPhone(phone);
        return builder.build();
    }

    public static void validThirdSourcePermission(Long employeeId){
        var existEmployee = Employee.queryEmployeeById(employeeId);
        if(existEmployee.isThirdSource())throw new NotAllowedModifyThirdSourceException();
    }

    public boolean isThirdSource(){
        return dataSource != DataSource.LOCAL;
    }

    public static Employee queryOrCreateEmployeeByType(Long orgId,EmployeeType employeeType){
        Preconditions.checkArgument(employeeType!=EmployeeType.EMPLOYEE_USER,"不支持创建普通用户的雇员");
        var exists = getRepository().queryEmployeeByType(orgId,employeeType);
        if(Objects.nonNull(exists)){
            return exists;
        }

        var created = new Employee();
        created.setName(employeeType.name());
        created.setOrgId(orgId);
        created.setRelateUserId(employeeType.name() + "-" + created.getId());
        created.setUserId(-1L);
        var createdEmployee =  getRepository().save(created);
        var organization = Organization.queryOrganizationByOrgId(orgId);
        if(Objects.isNull(organization))throw new OrganizationNotExistsException(orgId);

        EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, organization);
        return createdEmployee;
    }

    /**
     * 为指定组织创建一个雇员
     * @param orgId 组织ID
     * @return 创建成功的employee信息
     */
    public Employee createEmployeeForOrg(Long orgId){
        var organization = Organization.queryOrganizationByOrgId(orgId);
        if(Objects.isNull(organization))throw new OrganizationNotExistsException(orgId);

        if(Strings.isNullOrEmpty(phone) && Strings.isNullOrEmpty(email)) throw new PhoneEmailCanNotAllEmptyException();
        this.orgId = orgId;

        checkEmployeeUserId(orgId);
        checkEmployeeEmail(orgId);
        checkEmployeePhone(orgId);

        this.checkOrAssignEmployeeId();
        this.createUserFromEmployee();
        setCreateTime(System.currentTimeMillis());
        return getRepository().save(this);
    }

    private void checkEmployeePhone(Long orgId) {
        if(!Strings.isNullOrEmpty(phone)){
            var existsPhone = getRepository().queryOrgEmployeeByPhone(orgId,phone);
            if(Objects.nonNull(existsPhone))throw new EmployeePhoneExistsException(phone);
        }
    }

    private void checkEmployeeEmail(Long orgId) {
        if(!Strings.isNullOrEmpty(email)){
            var existsEmail = getRepository().queryOrgEmployeeByEmail(orgId,email);
            if(Objects.nonNull(existsEmail))throw new EmployeeEmailExistsException(email);
        }
    }

    private void checkEmployeeUserId(Long orgId) {
        if(this.userId > 0) {
            var existUser = getRepository().queryOrgEmployeeByUserId(orgId,userId);
            if(Objects.nonNull(existUser))throw new EmployeeUserIdExistsException(userId);
        }
    }

    private void checkOrAssignEmployeeId(){
        if(Strings.isNullOrEmpty(employeeId)){
            employeeId = getEmployeeIdGenerator().randomEmployeeId();
        }

        var exists = getRepository().isEmployeeIdExists(orgId,employeeId);
        if(exists)throw new EmployeeIdExistsException(employeeId);
    }

    private void createUserFromEmployee(){
        var builder = UserDto.newBuilder();
        builder.setName(name);
        if(Objects.nonNull(email))builder.setEmail(email);
        if(Objects.nonNull(phone))builder.setPhone(phone);
        builder.setId(getId());
        builder.setUserId(String.valueOf(getId()));

        getUserApplication().createLocalUser(builder.build());

        this.userId = getId();
    }

    /**
     * 一个雇员从外部导入进来
     * @param orgId 要导入的组织
     */
    public void importFromExternal(Long orgId){
        var organization = Organization.queryOrganizationByOrgId(orgId);
        if(Objects.isNull(organization))throw new OrganizationNotExistsException(orgId);

        if(Strings.isNullOrEmpty(phone) && Strings.isNullOrEmpty(email)) throw new PhoneEmailCanNotAllEmptyException();
        if(Strings.isNullOrEmpty(employeeId)) throw new EmployeeIdEmptyException();

        //如果员工ID已存在，则忽略此导入，但不报错
        var exists = getRepository().isEmployeeIdExists(orgId,employeeId);
        if(exists)return;

        this.orgId = orgId;
        this.createUserFromEmployee();
        this.relateUserId = employeeType + "-" + getId();
        setCreateTime(System.currentTimeMillis());
        var createdEmployee = getRepository().save(this);


        //创建部门
        if(!Strings.isNullOrEmpty(orgFullPath)){
            var deptTrees = orgFullPath.split("/");
            var parentOrg = organization;
            for (String deptName : deptTrees) {
                var queryDept = getOrganizationRepository().queryOrganizationByParentIdAndName(parentOrg.getId(),deptName);
                if(Objects.isNull(queryDept)){
                    var department = new Department();
                    department.setName(deptName);
                    department.setParent(parentOrg);
                    parentOrg = department.createDepartment();
                }
            }
            EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee.getId(),parentOrg.getId());
        }
    }
}
