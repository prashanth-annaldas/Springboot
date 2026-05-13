package com.example.One_Many.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Faculty_O_M {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_id")
    private List<Student_O_M> students;

    public List<Student_O_M> getStudents() {
        return students;
    }

    public void setStudents(List<Student_O_M> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
