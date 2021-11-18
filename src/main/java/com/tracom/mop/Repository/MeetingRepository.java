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

    @Query("SELECT COUNT(m.id) FROM Meeting m WHERE m.room.organization.id =?1")
    int numberOfMeetingsInOrganization(int id);

//    @Query("SELECT m FROM Meeting m WHERE m.room.organization.id = ?1 AND m.date >= CURRENT_DATE ORDER BY DATE(m.date) ASC, TIME(m.start_time) ASC")
//    List<Meeting> findMeetingByOrganizationOrderByTimeAndLaterDate(int id);
//
    @Query("SELECT m FROM Meeting m WHERE m.room.organization.id = ?1 AND m.date = CURRENT_DATE")
    List<Meeting> findMeetingByOrganizationAndToday(int id);

}
