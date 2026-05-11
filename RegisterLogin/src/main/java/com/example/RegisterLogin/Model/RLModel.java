package com.example.RegisterLogin.Model;

import org.springframework.stereotype.Component;

@Component
public class RLModel {
    private String username;
    private String pass;
    private String cPass;

    public String getcPass() {
        return cPass;
    }

    public void setcPass(String cPass) {
        this.cPass = cPass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
