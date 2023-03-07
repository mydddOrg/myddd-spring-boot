package org.myddd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.Employee;

import jakarta.transaction.Transactional;

@Transactional
class BaseIDEntityTest extends AbstractTest{

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

    @Test
    void testEquals(){
        var employee1 = randomEmployee(1L);
        var employee2 = randomEmployee(1L);
        var employee3 = randomEmployee(3L);
        Assertions.assertEquals(employee1,employee2);
        Assertions.assertNotEquals(employee1,employee3);
    }

    private Employee randomEmployee(Long id){
        var employee = new Employee();
        employee.setId(id);
        return employee;
    }
}