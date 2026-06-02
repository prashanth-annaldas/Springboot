package com.example.StudentManagementSystem_Backend.repository;

import com.example.StudentManagementSystem_Backend.entity.Faculty;
import com.example.StudentManagementSystem_Backend.entity.Student;
import com.example.StudentManagementSystem_Backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacultyRepo extends JpaRepository<Faculty, Long> {
    List<Faculty> findByDepartment(String department);
    Optional<Faculty> findByUser(Users user);
}
