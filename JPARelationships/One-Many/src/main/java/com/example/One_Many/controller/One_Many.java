package com.example.One_Many.controller;

import com.example.One_Many.entity.Faculty_O_M;
import com.example.One_Many.entity.Student_O_M;
import com.example.One_Many.repository.Faculty_O_M_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class One_Many {

    @Autowired
    private Faculty_O_M_Repo repo;

    @PostMapping("/onetomany")
    public Faculty_O_M save(@RequestBody Faculty_O_M f){

        Faculty_O_M existingFaculty =
                repo.findByName(f.getName());

        if (existingFaculty != null) {

            existingFaculty.getStudents().addAll(f.getStudents());
            return repo.save(existingFaculty);
        }

        return repo.save(f);
    }
}
