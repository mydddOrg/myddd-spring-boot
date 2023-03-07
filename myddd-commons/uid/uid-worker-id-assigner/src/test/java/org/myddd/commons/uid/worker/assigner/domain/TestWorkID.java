package org.myddd.commons.uid.worker.assigner.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.uid.worker.assigner.AbstractTest;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@Transactional
class TestWorkID extends AbstractTest {

    @Test
    void testCreateWorkId(){
        var randomWorkId = randomWorkID();
        var created = randomWorkId.createWorkId();
        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.getId() > 0);

        var notValidWorkId = new WorkID();
        notValidWorkId.setHost(created.getHost());
        notValidWorkId.setPort(created.getPort());
        Assertions.assertThrows(PersistenceException.class,notValidWorkId::createWorkId);
    }

    @Test
    void testQueryWorkId(){
        var notExistsWorkId = WorkID.queryWorkId(randomId(),8008);
        Assertions.assertNull(notExistsWorkId);

        var created = randomWorkID().createWorkId();
        Assertions.assertNotNull(WorkID.queryWorkId(created.getHost(),created.getPort()));
    }
}
