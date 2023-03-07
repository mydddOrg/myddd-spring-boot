package org.myddd.commons.uid.worker;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.uid.AbstractTest;

import jakarta.inject.Inject;

class WorkerIdAssignerTest extends AbstractTest {

    @Inject
    private WorkerIdAssigner workerIdAssigner;

    @Inject
    private UidGenerator uidGenerator;

    @Test
    void testNotNull(){
        Assertions.assertNotNull(workerIdAssigner);
    }

    @Test
    void testNextId(){
        var nextId = uidGenerator.getUID();
        Assertions.assertTrue(nextId > 0);
    }

}
