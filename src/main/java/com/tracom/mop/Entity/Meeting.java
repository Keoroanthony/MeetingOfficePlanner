package com.tracom.mop.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int meeting_id;
    @Column(nullable = false, length = 100)
    private String meeting_name;
    private String meeting_description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "boardroom", referencedColumnName = "boardroom_id")
    private BoardRoom boardRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Resources> resources = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "notifications_table",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "notification_id"),
            foreignKey= @ForeignKey(name = "employee_id"),
            inverseForeignKey = @ForeignKey(name = "boardroom_id"))
    private Set<Notifications> notifications = new HashSet<>();


}
