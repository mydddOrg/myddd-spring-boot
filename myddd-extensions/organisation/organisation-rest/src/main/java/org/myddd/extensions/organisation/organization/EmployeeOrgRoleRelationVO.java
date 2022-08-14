package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.EmployeeOrgRoleRelationDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeOrgRoleRelationVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private long employeeId;

    @JsonSerialize(using= ToStringSerializer.class)
    private long roleId;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public EmployeeOrgRoleRelationDto toDto(){
        return EmployeeOrgRoleRelationDto
                .newBuilder()
                .setEmployeeId(employeeId)
                .setOrgRoleId(roleId)
                .build();
    }

    public EmployeeOrgRoleRelationVO of(EmployeeOrgRoleRelationDto dto){
        EmployeeOrgRoleRelationVO roleRelationVO = new EmployeeOrgRoleRelationVO();
        roleRelationVO.setRoleId(dto.getOrgRoleId());
        roleRelationVO.setEmployeeId(dto.getEmployeeId());
        return roleRelationVO;
    }
}
