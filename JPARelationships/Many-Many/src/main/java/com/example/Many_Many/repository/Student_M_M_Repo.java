package com.example.Many_Many.repository;

import com.example.Many_Many.entity.Student_M_M;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Student_M_M_Repo extends JpaRepository<Student_M_M, Integer> {
}
