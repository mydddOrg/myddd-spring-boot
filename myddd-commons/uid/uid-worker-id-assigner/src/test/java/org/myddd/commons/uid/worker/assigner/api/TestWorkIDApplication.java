package org.myddd.commons.uid.worker.assigner.api;

import org.myddd.commons.uid.worker.assigner.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

public class TestWorkIDApplication extends AbstractTest {

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
