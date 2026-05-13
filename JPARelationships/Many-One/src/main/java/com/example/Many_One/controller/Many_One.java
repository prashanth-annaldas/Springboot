package com.example.Many_One.controller;

import com.example.Many_One.entity.Department_M_O;
import com.example.Many_One.entity.Student_M_O;
import com.example.Many_One.repository.Department_M_O_Repo;
import com.example.Many_One.repository.Student_M_O_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Many_One {
    @Autowired
    private Student_M_O_Repo student_m_o_repo;

    @Autowired
    private Department_M_O_Repo department_m_o_repo;

    @PostMapping("/manytoone")
    public Student_M_O save(@RequestBody Student_M_O s) {
        String deptName =
                s.getDepartmentManyOne().getName();

        Department_M_O existingDept =
                department_m_o_repo.findByName(deptName);

        if (existingDept != null) {

            s.setDepartmentManyOne(existingDept);

        } else {

            Department_M_O savedDept =
                    department_m_o_repo.save(
                            s.getDepartmentManyOne());

            s.setDepartmentManyOne(savedDept);
        }

        return student_m_o_repo.save(s);

    }
}