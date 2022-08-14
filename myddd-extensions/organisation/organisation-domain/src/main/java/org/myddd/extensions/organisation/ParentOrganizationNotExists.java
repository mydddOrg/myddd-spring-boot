package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class ParentOrganizationNotExists extends BusinessException {

    public ParentOrganizationNotExists(){
        super(OrganizationErrorCode.PARENT_ORG_NOT_EXISTS);
    }
}
