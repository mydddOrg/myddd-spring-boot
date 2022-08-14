package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.EmployeeDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private long id;

    private Long userId;

    private String employeeId;

    private String name;

    private long createTime;

    private String phone;

    private String email;

    private Long orgId;

    private String dataSource;

    private boolean isSystemManager;

    private List<OrganizationVO> organizations = List.of();

    private List<OrgRoleVO> roles = List.of();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<OrganizationVO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrganizationVO> organizations) {
        this.organizations = organizations;
    }

    public List<OrgRoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<OrgRoleVO> roles) {
        this.roles = roles;
    }

    public boolean isSystemManager() {
        return isSystemManager;
    }

    public void setSystemManager(boolean systemManager) {
        isSystemManager = systemManager;
    }
    public EmployeeDto toDto(){
        EmployeeDto.Builder builder =  EmployeeDto.newBuilder()
                .setId(id);
        if(Objects.nonNull(userId)) builder.setUserId(userId);
        if(Objects.nonNull(employeeId))builder.setEmployeeId(employeeId);
        if(createTime > 0) builder.setCreateTime(createTime);
        if(Objects.nonNull(phone))builder.setPhone(phone);
        if(Objects.nonNull(email))builder.setEmail(email);
        if(Objects.nonNull(name))builder.setName(name);
        if(Objects.nonNull(orgId))builder.setOrgId(orgId);
        if(Objects.nonNull(organizations))builder.addAllOrganization(organizations.stream().map(OrganizationVO::toDTO).collect(Collectors.toList()));
        if(Objects.nonNull(roles))builder.addAllRole(roles.stream().map(OrgRoleVO::toDto).collect(Collectors.toList()));

        return builder.build();
    }

    public static EmployeeVO of(EmployeeDto dto){
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setId(dto.getId());
        employeeVO.setUserId(dto.getUserId());
        employeeVO.setEmployeeId(dto.getEmployeeId());
        employeeVO.setCreateTime(dto.getCreateTime());
        employeeVO.setPhone(dto.getPhone());
        employeeVO.setEmail(dto.getEmail());
        employeeVO.setName(dto.getName());
        employeeVO.setOrgId(dto.getOrgId());
        employeeVO.setDataSource(dto.getDataSource());

        employeeVO.setOrganizations(dto.getOrganizationList().stream().map(OrganizationVO::of).collect(Collectors.toList()));
        employeeVO.setRoles(dto.getRoleList().stream().map(OrgRoleVO::of).collect(Collectors.toList()));
        return employeeVO;
    }
}
