package com.example.JWT_Authentication.controller;

import com.example.JWT_Authentication.entity.Client;
import com.example.JWT_Authentication.repository.ClientRepo;
import com.example.JWT_Authentication.service.ClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ClientController {

    @Autowired
    ClientRepo repo;

    @Autowired
    ClientService clientService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping("/myregister")
    public String register(
            @RequestBody Client user
    ){

        if(repo.findByUsername(
                user.getUsername())
                != null){

            return "ALREADY_EXIST";
        }

        Client client = new Client();

        client.setUsername(
                user.getUsername());

        client.setPassword(
                encoder.encode(
                        user.getPassword()
                )
        );

        repo.save(client);

        return "REGISTER_SUCCESS";
    }

    @PostMapping("/mylogin")
    public String login(
            @RequestBody Client user,
            HttpServletResponse response
    ){

        Client dbClient =
                repo.findByUsername(
                        user.getUsername()
                );

        if(dbClient != null &&
                encoder.matches(
                        user.getPassword(),
                        dbClient.getPassword()
                )){

            String token =
                    clientService.generateToken(
                            user.getUsername()
                    );

            Cookie cookie =
                    new Cookie("jwt", token);

            cookie.setHttpOnly(true);

            cookie.setSecure(false);

            cookie.setPath("/");

            cookie.setMaxAge(60 * 60);

            response.addCookie(cookie);

            if(dbClient.getDob() == null){

                return "PROFILE_REQUIRED";
            }

            return "LOGIN_SUCCESS";
        }

        return "INVALID_CREDENTIALS";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(
            @RequestBody Client req
    ){

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        Client client =
                repo.findByUsername(username);

        client.setDob(req.getDob());

        repo.save(client);

        return "PROFILE_SAVED";
    }

    @GetMapping("/home")
    public Client home(){

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return repo.findByUsername(username);
    }

    @GetMapping("/mylogout")
    public String logout(
            HttpServletResponse res
    ){

        Cookie cookie =
                new Cookie("jwt", "");

        cookie.setHttpOnly(true);

        cookie.setSecure(false);

        cookie.setPath("/");

        cookie.setMaxAge(0);

        res.addCookie(cookie);

        SecurityContextHolder.clearContext();

        return "LOGOUT_SUCCESS";
    }
}