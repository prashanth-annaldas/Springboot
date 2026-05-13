package com.example.Many_One.repository;

import com.example.Many_One.entity.Student_M_O;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Student_M_O_Repo extends JpaRepository<Student_M_O, Integer> {
}
