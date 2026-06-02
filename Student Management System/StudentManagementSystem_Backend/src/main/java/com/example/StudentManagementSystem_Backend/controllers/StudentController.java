package com.example.StudentManagementSystem_Backend.controllers;

import com.example.StudentManagementSystem_Backend.DTO.EnrollmentResponseDTO;
import com.example.StudentManagementSystem_Backend.DTO.FacultyEnrollmentDTO;
import com.example.StudentManagementSystem_Backend.DTO.StudentResponseDTO;
import com.example.StudentManagementSystem_Backend.entity.Course;
import com.example.StudentManagementSystem_Backend.entity.Faculty;
import com.example.StudentManagementSystem_Backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private AuthService authService;

    @GetMapping("/courses")
    public List<Course> getStudentEnrolledCourses(Authentication authentication){
        String email = authentication.getName();
        return authService.getCoursesByDepartment(email);
    }

    @PostMapping("/enroll/{courseId}")
    public String enrollCourse(@PathVariable Long courseId, Authentication authentication){
        String email = authentication.getName();
        return authService.enrollCourse(email, courseId);
    }

    @GetMapping("/enrollments")
    public List<EnrollmentResponseDTO> getEnrollments(Authentication authentication){
        return authService.getMyEnrollments(authentication.getName());
    }

    @GetMapping("/profile")
    public StudentResponseDTO profile(Authentication authentication){
        return authService.getStudentDetails(authentication.getName());
    }
}
