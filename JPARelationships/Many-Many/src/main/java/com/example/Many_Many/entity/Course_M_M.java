package com.example.Many_Many.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Course_M_M {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student_M_M> students;

    public Course_M_M() {

    }

    public Course_M_M(int id, String name, List<Student_M_M> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Student_M_M> getStudents() {
        return students;
    }

    public void setStudents(List<Student_M_M> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
