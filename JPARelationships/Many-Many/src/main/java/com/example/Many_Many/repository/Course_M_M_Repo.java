package com.example.Many_Many.repository;

import com.example.Many_Many.entity.Course_M_M;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Course_M_M_Repo extends JpaRepository<Course_M_M, Integer> {
}
