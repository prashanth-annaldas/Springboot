package com.example.StudentManagementSystem_Backend.repository;

import com.example.StudentManagementSystem_Backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByName(String name);
    List<Users> findByStatus(Users.Status status);
}
