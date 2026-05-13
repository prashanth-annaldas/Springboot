package com.example.One_Many.repository;

import com.example.One_Many.entity.Faculty_O_M;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Faculty_O_M_Repo extends JpaRepository<Faculty_O_M, Integer> {
    Faculty_O_M findByName(String name);
}
