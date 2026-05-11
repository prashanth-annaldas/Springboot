package com.example.RegisterLogin.Repository;

import com.example.RegisterLogin.Model.RLModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DAOModel {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(RLModel rlModel){
        String sql = "insert into userpass(uname, pass) values(?, ?)";
        jdbcTemplate.update(sql, rlModel.getUsername(), rlModel.getPass());
    }

    public boolean searchLogin(RLModel rlModel){
        String sql = "select count(*) from userpass where uname = ? and pass = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rlModel.getUsername(), rlModel.getPass());
        return count != null && count > 0;
    }

    public boolean searchRegister(RLModel rlModel){
        String sql = "select count(*) from userpass where uname = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rlModel.getUsername());
        return count == 0;
    }

    public boolean userExists(String username){
        String sql = "SELECT COUNT(*) FROM userpass WHERE uname=?";
        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, username
        );
        return count != null && count > 0;
    }

    public void changePassword(String username, String newPass){
        String sql = "UPDATE userpass SET pass=? WHERE uname=?";
        jdbcTemplate.update(sql, newPass, username);
    }

    public void deleteAcc(String uname){
        String sql = "delete from userpass where uname = ?";
        jdbcTemplate.update(sql, uname);
    }
}
