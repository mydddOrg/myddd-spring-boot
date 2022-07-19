package org.myddd.extensions.organisation.domain;

import com.google.common.base.Preconditions;
import org.myddd.extensions.organisation.OrganizationNameExistsException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Department")
public class Department extends Organization{

    public Department createDepartment(){
        Preconditions.checkNotNull(getParent(),"部门必须有一个父组织");
        var exists = isOrganizationNameExists(getParent().getId(),getName());
        if(exists)throw new OrganizationNameExistsException(getName());

        setFullPath(getParent().getFullPath() + getParent().getId() + PATH_SPLIT);
        setRootParentId(getParent().getRootParentId());
        setCreateTime(System.currentTimeMillis());
        return getRepository().save(this);
    }
}
