package com.tracom.mop.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "notifications")
public class Notifications {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO)
    private int notification_id;
    private String notification;

    @ManyToMany(mappedBy = "notifications", cascade = CascadeType.ALL)
    private Set<Meeting> meetings;

}
