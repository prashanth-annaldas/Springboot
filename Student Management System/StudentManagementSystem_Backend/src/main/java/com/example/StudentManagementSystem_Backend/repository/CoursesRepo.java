package com.example.StudentManagementSystem_Backend.repository;

import com.example.StudentManagementSystem_Backend.entity.Course;
import com.example.StudentManagementSystem_Backend.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursesRepo extends JpaRepository<Course, Long> {
    boolean existsByCourseName(String courseName);
    List<Course> findByFaculty_Department(String department);
}
