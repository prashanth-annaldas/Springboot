package com.example.StudentManagementSystem_Backend.repository;

import com.example.StudentManagementSystem_Backend.entity.Student;
import com.example.StudentManagementSystem_Backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(Users user);
}
