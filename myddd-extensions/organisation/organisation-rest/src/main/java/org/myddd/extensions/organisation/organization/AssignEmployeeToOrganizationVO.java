package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.AssignEmployeeToOrganizationDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignEmployeeToOrganizationVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private long employeeId;

    @JsonSerialize(using= ToStringSerializer.class)
    private long orgId;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long operator;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public AssignEmployeeToOrganizationDto toDto(){
        return AssignEmployeeToOrganizationDto.newBuilder()
                .setOrgId(orgId)
                .setEmployeeId(employeeId)
                .build();
    }
}
