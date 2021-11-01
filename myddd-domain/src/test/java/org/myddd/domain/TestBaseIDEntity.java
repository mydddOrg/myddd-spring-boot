package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.Employee;

import javax.transaction.Transactional;

@Transactional
class TestBaseIDEntity extends AbstractTest{

    @Test
    void testBaseIDEntity(){
        Employee randomEmployee = randomEmployee();
        Assertions.assertNull(randomEmployee.getId());
        Assertions.assertFalse(randomEmployee.existed());
        Assertions.assertTrue(randomEmployee.notExisted());

        Employee created = randomEmployee.createEmployee();
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertTrue(created.existed());
        Assertions.assertFalse(created.notExisted());
    }
}
