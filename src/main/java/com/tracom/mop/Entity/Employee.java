package com.tracom.mop.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
//@SecondaryTable(name = "contactdetails", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
//@SecondaryTable(name = "users", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class Employee {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 50)
    private String employee_name;
    @Column(nullable = false, length = 10)
    private String gender;
    @Column(nullable = false, length = 10)
    private String category;

    //contact details
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 20)
    private String phone;

    @Column( nullable = false, length = 20)
    private String username;
    @Column(nullable = false)
    private String password;


    //****************Relationships*********************//
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, referencedColumnName = "id")
    private Organization organization;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false, referencedColumnName = "id")
    private Department department;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Meeting> meetings = new ArrayList<>();

    //Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

}
