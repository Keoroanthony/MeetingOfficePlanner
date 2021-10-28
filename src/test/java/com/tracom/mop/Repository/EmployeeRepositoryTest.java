package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Department;
import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Entity.Organization;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testCreateUser() {

            Employee employee =new Employee();
            employee.setId(2);
            employee.setEmployee_name("kimani");
            employee.setEmail("kimani.kim@gmail.com");
            employee.setPassword("Mungiki1");
            employee.setRoles("admin");
            employee.setGender("male");
            employee.setPhone("0712334456");
            employee.setUsername("kimani");
            employee.setDepartment(Department.builder().build());
            employee.setOrganization(Organization.builder().build());

            Employee saveEmployee=employeeRepository.save(employee);
            Assertions.assertThat(saveEmployee).isNotNull();
            Assertions.assertThat(saveEmployee.getId()).isGreaterThan(0);
        }

    }
