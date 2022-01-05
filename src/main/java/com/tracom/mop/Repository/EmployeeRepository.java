package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT u FROM Employee u WHERE u.email = ?1")
    Employee findByEmail(@Param("email")String email);
    //admin hasn't assigned role
    @Query("SELECT COUNT(u.id) FROM Employee u WHERE u.organization.id = ?1 AND u.enabled = false ")
    int numberOfUnauthorizedUsers(int id);

    //admin has assigned role but user hasn't set password
    @Query("SELECT COUNT(u.id) FROM Employee u WHERE u.organization.id = ?1 AND u.enabled = true AND u.password IS NULL")
    int numberOfPendingUsers(int id);

    //User has role and set password
    @Query("SELECT COUNT(u.id) FROM Employee u WHERE u.organization.id = ?1 AND u.password IS NOT NULL")
    int numberOfAuthorizedUsers(int id);

    @Modifying
    @Query("UPDATE Employee u SET u.roles = :roles, u.enabled = :enabled WHERE u.email = :email")
    void findEmployeesByEmail(@Param(value = "email") String email, @Param(value = "roles")Set<Role> roles, @Param(value = "enabled") Boolean enabled);


    @Modifying
    @Query("UPDATE Employee u SET u.employee_name = :employee_name, u.phone = :phone WHERE u.id = :id")
    void updateUserDetails(@Param(value = "id") int id, @Param(value = "employee_name") String employee_name, @Param(value = "phone") String phone);

    //List of authorised users
    @Query("SELECT u FROM Employee u WHERE u.email IS NOT NULL AND u.organization.id = ?1 AND u.password IS NOT NULL")
    List<Employee> findAllEmailByOrganization(int id);

    //List of pending users
    @Query("SELECT u FROM Employee u WHERE u.email IS NOT NULL AND u.organization.id = ?1 AND u.password IS NULL AND u.enabled = true")
    List<Employee> findAllWithoutPasswordByOrganization(int id);

    //List of unauthorised users
    @Query("SELECT u FROM Employee u WHERE u.email IS NOT NULL AND u.organization.id = ?1 AND u.enabled = false ")
    List<Employee> findAllUnauthorisedByOrganization(int id);

    @Modifying
    @Query("UPDATE Employee u SET u.password = :password WHERE u.id = :id")
    void updateUserPassword(@Param(value = "password") String password, @Param(value = "id") int userId);

    Employee findByResetPasswordToken(String token);

    Employee findBySetPasswordToken(String token);
}
