package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT u FROM Employee u WHERE u.email = ?1")
    Employee findByEmail(@Param("email")String email);
}
