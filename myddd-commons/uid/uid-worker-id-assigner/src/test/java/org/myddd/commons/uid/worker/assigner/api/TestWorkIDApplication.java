package org.myddd.commons.uid.worker.assigner.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.uid.worker.assigner.AbstractTest;

import javax.inject.Inject;

class TestWorkIDApplication extends AbstractTest {

    @Inject
    private WorkIDApplication workIDApplication;

    @Test
    void testCreateWorkId(){
        var created = workIDApplication.createWorkId(randomWorkIdDto());
        Assertions.assertNotNull(created);
    }

    @Test
    void testQueryWorkId(){
        var notExists = workIDApplication.queryWorkId(randomId(),randomInt());
        Assertions.assertNull(notExists);

        var created = workIDApplication.createWorkId(randomWorkIdDto());
        Assertions.assertNotNull(workIDApplication.queryWorkId(created.getHost(), created.getPort()));
    }
}
