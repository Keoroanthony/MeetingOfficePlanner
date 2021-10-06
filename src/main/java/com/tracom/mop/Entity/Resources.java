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
@Table(name = "resources")
public class Resources {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int resources_id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boardroom", referencedColumnName = "meeting_id")
    private Meeting meeting;
}
