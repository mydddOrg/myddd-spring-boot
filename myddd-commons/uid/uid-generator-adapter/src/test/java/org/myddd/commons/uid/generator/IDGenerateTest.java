package org.myddd.commons.uid.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.uid.AbstractTest;
import org.myddd.domain.IDGenerate;

import javax.inject.Inject;

class IDGenerateTest extends AbstractTest {

    @Inject
    private IDGenerate idGenerate;

    @Test
    void testNextId(){
        var nextId = idGenerate.nextId();
        Assertions.assertTrue(nextId > 0);
    }
}
