package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    @Query("SELECT i FROM Organization i WHERE i.id = ?1")
    Organization findById(int id);
}
