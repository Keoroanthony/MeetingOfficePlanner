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
@Table(name = "boardroom")
public class BoardRoom {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO)
    private int boardroom_id;
    @Column(nullable = false, length = 50)
    private  String boardroom_name;
    @OneToOne(mappedBy = "boardRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Meeting meeting;

}
