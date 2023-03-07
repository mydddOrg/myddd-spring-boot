package org.myddd.commons.uid.worker;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.exception.UidGenerateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.uid.AbstractTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.inject.Inject;

@ActiveProfiles({ "no-exists" })
class BadServerNameWorkerIdAssignerTest extends AbstractTest {


    @Inject
    private UidGenerator uidGenerator;

    @Test
    void testNextId(){
        Assertions.assertThrows(UidGenerateException.class,()->uidGenerator.getUID());
    }
}
