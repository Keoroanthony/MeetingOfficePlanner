package com.tracom.mop.Service;

import com.tracom.mop.Entity.Meeting;
import com.tracom.mop.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {
    private MeetingRepository meetingRepository;
    @Autowired
    public MeetingService(MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
    }
    //CREATE
    public void saveMeeting(Meeting meeting){
        meetingRepository.save(meeting);
    }
    //RETRIEVE
    public Meeting getByMeeting_name(String meeting_name) {
        return meetingRepository.findByMeeting_name(meeting_name);
    }
    public List<Meeting> getAllByOrganization(int id) {
        return meetingRepository.findAllByOrganization(id);
    }
    // UPDATE
    public Meeting updateMeeting(int id){
        return meetingRepository.findById(id).get();
    }
    // DELETE
    public void deleteResource(int id){
        meetingRepository.deleteById(id);
    }
}
