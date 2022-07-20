package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.OrgRoleGroupNotEmptyException;
import org.myddd.extensions.organisation.OrgRoleGroupNotExistsException;

import javax.transaction.Transactional;

@Transactional
class OrgRoleGroupTest extends AbstractTest {

    @Test
    void testCreateOrgRoleGroup(){
        var created = randomCreateOrgRoleGroup();
        Assertions.assertNotNull(created);

        var orgRoleGroup = new OrgRoleGroup();

        Assertions.assertThrows(IllegalArgumentException.class, orgRoleGroup::createRoleGroup);
    }

    @Test
    void testUpdateOrgRoleGroup(){
        var notExistOrgRoleGroup = new OrgRoleGroup();
        notExistOrgRoleGroup.setId(randomLong());
        Assertions.assertThrows(OrgRoleGroupNotExistsException.class, notExistOrgRoleGroup::update);

        var created = randomCreateOrgRoleGroup();
        Assertions.assertNotNull(created);
        var newName = randomId();
        created.setName(newName);
        Assertions.assertDoesNotThrow(created::update);
    }

    @Test
    void testQueryOrgRoleGroupByCompany(){
        var created = randomCreateOrgRoleGroup();
        Assertions.assertNotNull(created);

        Assertions.assertFalse(OrgRoleGroup.queryByCompany(created.getCompany().getId()).isEmpty());
    }

    @Test
    void testRemoveOrgRoleGroup(){
        var randomLong = randomLong();
        Assertions.assertThrows(OrgRoleGroupNotExistsException.class,()->OrgRoleGroup.removeOrgRoleGroup(randomLong));

        var orgRoleGroup = randomCreateOrgRoleGroup();
        Assertions.assertDoesNotThrow(()-> OrgRoleGroup.removeOrgRoleGroup(orgRoleGroup.getId()));

        var notEmptyOrgRoleGroup = randomCreateOrgRoleGroup();

        randomOrgRole(notEmptyOrgRoleGroup).createRole();
        var orgRoleGroupId = notEmptyOrgRoleGroup.getId();
        Assertions.assertThrows(OrgRoleGroupNotEmptyException.class,() -> OrgRoleGroup.removeOrgRoleGroup(orgRoleGroupId));

    }

    private OrgRoleGroup randomCreateOrgRoleGroup(){
        var company = randomCompany().createTopCompany();
        var created = randomOrgRoleGroup(company).createRoleGroup();
        Assertions.assertNotNull(created);
        return created;
    }

}
