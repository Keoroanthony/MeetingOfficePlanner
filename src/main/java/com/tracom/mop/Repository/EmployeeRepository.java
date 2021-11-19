package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT u FROM Employee u WHERE u.email = ?1")
    Employee findByEmail(@Param("email")String email);

    @Modifying
    @Query("UPDATE Employee u SET u.employee_name = :employee_name, u.phone = :phone WHERE u.id = :id")
    void updateUserDetails(@Param(value = "id") int id, @Param(value = "employee_name") String employee_name, @Param(value = "phone") String phone);

    @Query("SELECT u FROM Employee u WHERE u.email IS NOT NULL AND u.organization.id = ?1")
    List<Employee> findAllEmailByOrganization(int id);

    @Modifying
    @Query("UPDATE Employee u SET u.password = :password WHERE u.id = :id")
    void updateUserPassword(@Param(value = "password") String password, @Param(value = "id") int userId);

    Employee findByResetPasswordToken(String token);

    Employee findBySetPasswordToken(String token);
}
