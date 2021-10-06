package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT i FROM Employee i WHERE i.id = ?1")
    Department findByEmail(int id);
}
