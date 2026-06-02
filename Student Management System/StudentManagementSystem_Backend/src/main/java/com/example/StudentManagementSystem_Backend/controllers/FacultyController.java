package com.example.StudentManagementSystem_Backend.controllers;

import com.example.StudentManagementSystem_Backend.DTO.FacultyAndAdminResponseDTO;
import com.example.StudentManagementSystem_Backend.DTO.FacultyEnrollmentDTO;
import com.example.StudentManagementSystem_Backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
    @Autowired
    private AuthService authService;

    @GetMapping("/students")
    public List<FacultyEnrollmentDTO> getFacultyStudents(Authentication authentication){
        return authService.getFacultyStudents(authentication.getName());
    }

    @PutMapping("/grade/{enrollmentId}")
    public String updateGrade(@PathVariable Long enrollmentId,@RequestParam String grade){
        return authService.updateGrade(enrollmentId, grade);
    }

    @GetMapping("/profile")
    public FacultyAndAdminResponseDTO profile(Authentication authentication){
        return authService.getFacultyAndAdminDetails(authentication.getName());
    }
}
