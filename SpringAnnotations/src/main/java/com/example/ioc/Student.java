package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {
    private Study study;
    private Faculty faculty;
    @Autowired
    public Student(Study study, Faculty faculty){
        this.study = study;
        this.faculty = faculty;
    }
    public void recordsFaculty(){
        faculty.records();
    }
    public void details(){
        System.out.println("Student details");
        study.read();
    }
}
