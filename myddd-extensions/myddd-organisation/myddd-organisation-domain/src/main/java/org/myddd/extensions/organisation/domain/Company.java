package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import org.myddd.extensions.organisation.OrganizationNameExistsException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("Company")
public class Company extends Organization{

    public Company createTopCompany(){
        Preconditions.checkArgument(Objects.isNull(getParent()),"顶级组织不能有父组织");
        setFullPath(PATH_SPLIT);
        setRootParentId(getId());
        setCreateTime(System.currentTimeMillis());
        return getRepository().save(this);
    }

    public Company createSubCompany(){
        Preconditions.checkNotNull(getParent(),"需要指定一个父组织");
        var exists = isOrganizationNameExists(getParent().getId(),getName());
        if(exists)throw new OrganizationNameExistsException(getName());

        setFullPath(getParent().getFullPath()  + getParent().getId() + PATH_SPLIT);
        setRootParentId(getParent().getRootParentId());
        setCreateTime(System.currentTimeMillis());
        return getRepository().save(this);
    }

    public static List<Organization> queryTopCompaniesByUserId(Long userId){
        return getRepository().queryTopCompaniesByUserId(userId);
    }
}