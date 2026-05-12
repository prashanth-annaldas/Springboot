package com.example.College_many_one.controller;

import com.example.College_many_one.entity.Student_Many_One;
import com.example.College_many_one.repository.Student_Many_One_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class CollegeController {

    @Autowired
    private Student_Many_One_Repository repo;

    @PostMapping("/manytoone")
    public Student_Many_One save(@RequestBody Student_Many_One s){
        return repo.save(s);
    }
}
