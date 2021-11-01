package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.Organization;

import javax.transaction.Transactional;

@Transactional
class TestBaseEntity extends AbstractTest{

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

}
