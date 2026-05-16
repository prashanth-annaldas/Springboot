package com.example.JWT.controller;

import com.example.JWT.entity.User;
import com.example.JWT.repository.UserRepository;
import com.example.JWT.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

@RestController
public class JwtController {

    @Autowired
    UserRepository repo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    // REGISTER
    @PostMapping("/myregister")
    public User register(
            @RequestBody User user
    ) {

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        return repo.save(user);
    }

    // LOGIN
    @PostMapping("/mylogin")
    public String login(
            @RequestBody User user
    ) {

        User dbUser =
                repo.findByUsername(user.getUsername());

        if(dbUser != null &&
                encoder.matches(
                        user.getPassword(),
                        dbUser.getPassword()
                )) {

            return jwtService.generateToken(
                    user.getUsername()
            );
        }

        return "Invalid Credentials";
    }

    // PROTECTED API
    @GetMapping("/hello")
    public String hello() {
        return "JWT Protected API";
    }
}