package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT i FROM Room i WHERE i.id = ?1")
    Room findByRoom_name(@Param("room_name")String room_name);

    @Query("SELECT i FROM Room i WHERE i.id IS NOT NULL AND i.organization.id = ?1")
    List<Room> findAllByOrganization(int id);
}
