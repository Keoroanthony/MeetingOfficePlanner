package com.tracom.mop.Service;

import com.tracom.mop.Entity.Room;
import com.tracom.mop.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    //CREATE
    public void saveRoom(Room room){
        roomRepository.save(room);
    }
    //RETRIEVE
    public Room getByRoom_name(String room_name) {
        return roomRepository.findByRoom_name(room_name);
    }
    public List<Room> getAllByOrganization(int id) {
        return roomRepository.findAllByOrganization(id);
    }
    // UPDATE
    public Room updateRoom(int id){
        return roomRepository.findById(id).get();
    }
    // DELETE
    public void deleteResource(int id){
        roomRepository.deleteById(id);
    }
}
