package com.example.Many_One.repository;

import com.example.Many_One.entity.Department_M_O;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Department_M_O_Repo extends JpaRepository<Department_M_O, Integer> {
    Department_M_O findByName(String name);
}
