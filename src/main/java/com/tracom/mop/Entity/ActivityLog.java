package com.tracom.mop.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "activity")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int activity_id;
    private String activity;
}
