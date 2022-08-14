package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.JoinOrLeaveOrganizationDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinOrLeaveOrganizationVO {
    @JsonSerialize(using= ToStringSerializer.class)
    private Long userId;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long orgId;

    private Long operator;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public JoinOrLeaveOrganizationDto toDto(){
        return JoinOrLeaveOrganizationDto.newBuilder()
                .setUserId(userId)
                .setOrgId(orgId)
                .build();
    }


}
