package com.example.College_many_one.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;



@Entity
public class Student_Many_One {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department_Many_One departmentManyOne;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Department_Many_One getDepartmentManyOne() {
        return departmentManyOne;
    }
    public void setDepartmentManyOne(Department_Many_One departmentManyOne) {
        this.departmentManyOne = departmentManyOne;
    }
    public void setId(int id) {
        this.id = id;
    }
}
