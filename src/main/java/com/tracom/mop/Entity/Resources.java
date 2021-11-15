package com.tracom.mop.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resources")
public class Resources {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 50)
    private  String resource_name;

    public Resources() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "id=" + id +
                ", resource_name='" + resource_name + '\'' +
                '}';
    }
}

