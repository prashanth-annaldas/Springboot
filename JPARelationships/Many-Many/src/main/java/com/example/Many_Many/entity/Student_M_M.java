package com.example.Many_Many.entity;

import com.example.Many_Many.repository.Course_M_M_Repo;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Student_M_M {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course_M_M> courses;

    public Student_M_M() {
    }

    public Student_M_M(int id, String name, List<Course_M_M> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course_M_M> getCourses() {
        return courses;
    }

    public void setCourses(List<Course_M_M> courses) {
        this.courses = courses;
    }
}
