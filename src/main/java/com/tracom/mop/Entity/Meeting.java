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
    private String date_time;
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

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
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
                ", date_time='" + date_time + '\'' +
                '}';
    }
}