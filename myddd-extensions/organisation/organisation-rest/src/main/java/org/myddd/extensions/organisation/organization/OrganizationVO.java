package org.myddd.extensions.organisation.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.myddd.extensions.organization.api.OrganizationDto;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationVO {
    @JsonSerialize(using= ToStringSerializer.class)
    private long id;

    private String name;

    private long createTime;

    private OrganizationVO parent;

    private String path;

    private String category;

    private String creator;

    private Long createUserId;

    private String fullNamePath;

    private String dataSource;

    public OrganizationVO(){}

    public OrganizationVO(long id){
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setParent(OrganizationVO parent) {
        this.parent = parent;
    }

    public OrganizationVO getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setFullNamePath(String fullNamePath) {
        this.fullNamePath = fullNamePath;
    }

    public String getFullNamePath() {
        return fullNamePath;
    }

    public OrganizationDto toDTO(){
        OrganizationDto.Builder builder = OrganizationDto.newBuilder()
                .setId(id)
                .setParentId(Objects.isNull(parent)?0: parent.id)
                .setCategory(OrganizationDto.Category.COMPANY);

        if(Objects.nonNull(name))builder.setName(name);
        if(Objects.nonNull(createUserId))builder.setCreateUserId(createUserId);

        builder.setCreateTime(createTime);
        if(Objects.nonNull(path))builder.setPath(path);
        if(Objects.nonNull(parent))builder.setParentId(parent.id);
        return builder.build();
    }

    public static OrganizationVO of(OrganizationDto dto){
        OrganizationVO organizationVO = new OrganizationVO();
        organizationVO.id = dto.getId();
        organizationVO.name = dto.getName();
        organizationVO.createTime = dto.getCreateTime();
        organizationVO.path = dto.getPath();
        organizationVO.category = dto.getCategory().toString();
        organizationVO.createUserId = dto.getCreateUserId();
        organizationVO.dataSource = dto.getDataSource();
        organizationVO.setFullNamePath(dto.getFullNamePath());
        if(dto.getParentId() > 0){
            OrganizationVO parent = new OrganizationVO();
            parent.id = dto.getParentId();
            organizationVO.parent = parent;
        }
        return organizationVO;
    }


}
