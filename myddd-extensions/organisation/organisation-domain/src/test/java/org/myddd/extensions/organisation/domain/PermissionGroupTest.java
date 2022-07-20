package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;

import javax.transaction.Transactional;

@Transactional
class PermissionGroupTest extends AbstractTest {

    @Test
    void testCreatePermissionGroup(){
        PermissionGroup notValidPermissionGroup = new PermissionGroup();
        Assertions.assertThrows(IllegalArgumentException.class,notValidPermissionGroup::createPermissionGroup);

        notValidPermissionGroup.setType(PermissionGroupType.PERMISSION_GROUP_TYPE_FORM);
        Assertions.assertThrows(IllegalArgumentException.class,notValidPermissionGroup::createPermissionGroup);

        PermissionGroup validPermissionGroup = randomPermissionGroup();
        Assertions.assertNotNull(validPermissionGroup.createPermissionGroup());
    }
}
