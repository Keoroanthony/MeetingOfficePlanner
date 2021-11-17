package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    @Query("SELECT i FROM Meeting i WHERE i.id = ?1")
    Meeting findByMeeting_name(@Param("meeting_name")String meeting_name);

    @Query("SELECT i FROM Meeting i WHERE i.id IS NOT NULL AND i.room.organization.id = ?1")
    List<Meeting> findAllByOrganization(int id);
}
