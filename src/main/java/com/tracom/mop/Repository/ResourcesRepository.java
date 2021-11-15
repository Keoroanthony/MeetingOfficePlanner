package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResourcesRepository extends JpaRepository<Resources, Integer> {
    @Query("SELECT i FROM Resources i WHERE i.id = ?1")
    Resources findByResource_name(int id);
}
