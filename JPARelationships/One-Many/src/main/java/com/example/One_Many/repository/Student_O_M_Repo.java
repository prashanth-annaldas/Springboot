package com.example.One_Many.repository;

import com.example.One_Many.entity.Student_O_M;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Student_O_M_Repo extends JpaRepository<Student_O_M, Integer> {
}
