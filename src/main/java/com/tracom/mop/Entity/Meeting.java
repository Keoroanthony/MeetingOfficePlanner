package com.tracom.mop.Entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO)
    private int id;
    private String meeting_name;
    private String meeting_description;
    private int owner;
    private String date;
    private String start_time;
    private String end_time;
    @OneToOne(fetch = FetchType.EAGER)
    private Room room;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "co_owners_table",
            joinColumns = @JoinColumn(name = "meeting_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    private Set<Employee> co_owners = new HashSet<>();

    public Meeting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeeting_name() {
        return meeting_name;
    }

    public void setMeeting_name(String meeting_name) {
        this.meeting_name = meeting_name;
    }

    public String getMeeting_description() {
        return meeting_description;
    }

    public void setMeeting_description(String meeting_description) {
        this.meeting_description = meeting_description;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Set<Employee> getCo_owners() {
        return co_owners;
    }

    public void setCo_owners(Set<Employee> co_owners) {
        this.co_owners = co_owners;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", meeting_name='" + meeting_name + '\'' +
                ", meeting_description='" + meeting_description + '\'' +
                ", owner=" + owner +
                ", date='" + date + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
}