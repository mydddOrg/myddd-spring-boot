package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

class EmployeeIdGeneratorTest extends AbstractTest {

    private final Logger logger = LoggerFactory.getLogger(EmployeeIdGeneratorTest.class);

    @Inject
    private EmployeeIdGenerator employeeIdGenerator;


    @Test
    void testInstanceNotNull(){
        Assertions.assertNotNull(employeeIdGenerator);
    }

    @Test
    void testGenerateRandomEmployeeId(){
        var randomString = employeeIdGenerator.randomEmployeeId();
        logger.debug(randomString);
        Assertions.assertNotNull(randomString);
        Assertions.assertEquals(12,randomString.length());
    }

    @Test
    void testGenerateRandomEmployeeIdWithLength(){
        var randomString = employeeIdGenerator.randomEmployeeId(15);
        Assertions.assertNotNull(randomString);
        Assertions.assertEquals(15,randomString.length());
    }

    @Test
    void testGenerateRandomEmployeeIdWithPrefix(){
        var randomString = employeeIdGenerator.randomEmployeeId("weixin-");
        Assertions.assertNotNull(randomString);
        Assertions.assertTrue(randomString.startsWith("weixin-"));
    }


}
