package com.example.StudentManagementSystem_Backend.controllers;

import com.example.StudentManagementSystem_Backend.DTO.ForgotPasswordDTO;
import com.example.StudentManagementSystem_Backend.DTO.LoginRequestDTO;
import com.example.StudentManagementSystem_Backend.DTO.LoginResponseDTO;
import com.example.StudentManagementSystem_Backend.DTO.RegisterRequestDTO;
import com.example.StudentManagementSystem_Backend.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
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

    @GetMapping("/mylogout")
    public String myLogout(HttpServletResponse res){
        return authService.myLogout(res);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordDTO dto){
        return authService.forgotPassword(dto);
    }

}
