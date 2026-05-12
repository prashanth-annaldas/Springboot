package com.example.College_many_one.repository;

import com.example.College_many_one.entity.Student_Many_One;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Student_Many_One_Repository extends JpaRepository<Student_Many_One, Integer> {

}
