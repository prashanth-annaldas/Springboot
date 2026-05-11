package com.example.SpringBasic;

import com.mysql.cj.jdbc.JdbcConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class InfoModel {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void save(String name){
        String sql = "insert into nameid(uname) values(?)";
        jdbcTemplate.update(sql, name);
    }
}
