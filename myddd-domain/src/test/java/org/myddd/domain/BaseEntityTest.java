package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.Organization;

import javax.transaction.Transactional;

@Transactional
class BaseEntityTest extends AbstractTest{

    @Test
    void testExists(){
        Organization randomOrganization = randomOrganization();
        Assertions.assertFalse(randomOrganization.existed());


        Organization created = randomOrganization.createOrg();
        Assertions.assertTrue(created.existed());
    }

    @Test
    void testNotExists(){
        Organization randomOrganization = randomOrganization();
        Assertions.assertTrue(randomOrganization.notExisted());

        Organization created = randomOrganization.createOrg();
        Assertions.assertFalse(created.notExisted());
    }

    @Test
    void testSetId(){
        Organization randomOrganization = randomOrganization();

        String randomId = randomId();
        randomOrganization.setId(randomId);
        Assertions.assertEquals(randomId,randomOrganization.getId());
    }

    @Test
    void testEquals(){
        var idOne = randomId();
        var idTwo = randomId();
        var organization1 = randomOrganizationWithId(idOne);
        var organization2 = randomOrganizationWithId(idTwo);
        var organization3 = randomOrganizationWithId(idOne);
        Assertions.assertEquals(organization1,organization3);
        Assertions.assertNotEquals(organization1,organization2);
    }

    protected Organization randomOrganizationWithId(String id){
        var organization = new Organization();
        organization.setId(id);
        return organization;
    }

}
