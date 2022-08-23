package org.myddd.commons.uid.worker;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import org.myddd.commons.uid.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

public class WorkerIdAssignerTest extends AbstractTest {

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
