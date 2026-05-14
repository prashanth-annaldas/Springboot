package com.example.Many_Many.controller;

import com.example.Many_Many.entity.Course_M_M;
import com.example.Many_Many.entity.Student_M_M;
import com.example.Many_Many.repository.Student_M_M_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class Many_Many {
    @Autowired
    Student_M_M_Repo repo;

    @PostMapping("/save")
    public Student_M_M saveStudent(@RequestBody Student_M_M s) {

        return repo.save(s);
    }
}
