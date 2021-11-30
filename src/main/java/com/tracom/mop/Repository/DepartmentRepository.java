package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT i FROM Department i WHERE i.id = ?1")
    Department findByDepartment_name(int id);

    @Query("SELECT u FROM Department u WHERE u.organization.id= ?1")
    List<Department> existsDepartmentByOrganizationId(int id);

}
