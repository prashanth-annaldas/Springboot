package com.example.College.Controller;

import com.example.College.Entity.Student;
import com.example.College.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository repo;

    @PostMapping("/student")
    public Student save(@RequestBody Student s){
        return repo.save(s);
    }
}
