package com.example.StudentManagementSystem_Backend.controllers;

import com.example.StudentManagementSystem_Backend.DTO.LoginRequestDTO;
import com.example.StudentManagementSystem_Backend.DTO.LoginResponseDTO;
import com.example.StudentManagementSystem_Backend.DTO.RegisterRequestDTO;
import com.example.StudentManagementSystem_Backend.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class StudentController {
    @Autowired
    private AuthService authService;

    @PostMapping("/myregister")
    public String register(@RequestBody RegisterRequestDTO dto){
        return authService.register(dto);
    }

    @PostMapping("/mylogin")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto, HttpServletResponse res){
        return authService.login(dto, res);
    }

}
