package com.example.StudentManagementSystem_Backend.controllers;

import com.example.StudentManagementSystem_Backend.DTO.CourseRequestDTO;
import com.example.StudentManagementSystem_Backend.DTO.FacultyAndAdminResponseDTO;
import com.example.StudentManagementSystem_Backend.DTO.FacultyRequestDTO;
import com.example.StudentManagementSystem_Backend.DTO.StudentResponseDTO;
import com.example.StudentManagementSystem_Backend.entity.Faculty;
import com.example.StudentManagementSystem_Backend.entity.Users;
import com.example.StudentManagementSystem_Backend.repository.FacultyRepo;
import com.example.StudentManagementSystem_Backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private FacultyRepo facultyRepo;

    @GetMapping("/pending-students")
    public List<Users> getPendingStudents(){

        return authService.getPendingStudents();
    }

    @PutMapping("/approve/{id}")
    public String approveStudent(
            @PathVariable Long id
    ){

        return authService.approveStudent(id);
    }

    @PostMapping("/create-faculty")
    public String createFaculty(@RequestBody FacultyRequestDTO dto){
        return authService.createFaculty(dto);
    }

    @DeleteMapping("/remove-faculty/{id}")
    public String removeFaculty(@PathVariable Long id){
        return authService.removeFaculty(id);
    }

    @GetMapping("faculty-list")
    public List<Faculty> getFacultyList(){
        return facultyRepo.findAll();
    }

    @PostMapping("create-course")
    public String createCourse(@RequestBody CourseRequestDTO dto){
        return authService.createCourse(dto);
    }

    @GetMapping("/profile")
    public FacultyAndAdminResponseDTO profile(Authentication authentication){
        return authService.getFacultyAndAdminDetails(authentication.getName());
    }

}