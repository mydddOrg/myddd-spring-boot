package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.Employee;

import javax.transaction.Transactional;

@Transactional
class TestBaseIDEntity extends AbstractTest{

    @Test
    void testExists(){
        Employee randomEmployee = randomEmployee();
        Assertions.assertNull(randomEmployee.getId());
        Assertions.assertFalse(randomEmployee.existed());

        Employee created = randomEmployee().createEmployee();
        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.existed());
    }

    @Test
    void testNotExists(){
        Employee randomEmployee = randomEmployee();
        Assertions.assertTrue(randomEmployee.notExisted());

        Employee created = randomEmployee().createEmployee();
        Assertions.assertFalse(created.notExisted());
    }

    @Test
    void testSetId(){
        Employee randomEmployee = randomEmployee();

        randomEmployee.setId(1L);

        Assertions.assertEquals(1L,randomEmployee.getId());

        Assertions.assertThrows(RuntimeException.class, randomEmployee::createEmployee);
    }

}
