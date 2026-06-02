package com.example.StudentManagementSystem_Backend.repository;

import com.example.StudentManagementSystem_Backend.entity.Course;
import com.example.StudentManagementSystem_Backend.entity.Enrollment;
import com.example.StudentManagementSystem_Backend.entity.Faculty;
import com.example.StudentManagementSystem_Backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentsRepo extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentAndCourse(Student student, Course course);
    List<Enrollment> findByStudent(Student student);
    List<Enrollment> findByCourse_faculty(Faculty faculty);
}
