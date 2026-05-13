package com.example.Many_One.entity;

import jakarta.persistence.*;

@Entity
public class Student_M_O {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department_M_O department_m_o;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Department_M_O getDepartmentManyOne() {
        return department_m_o;
    }
    public void setDepartmentManyOne(Department_M_O department_m_o) {
        this.department_m_o = department_m_o;
    }
    public void setId(int id) {
        this.id = id;
    }
}
