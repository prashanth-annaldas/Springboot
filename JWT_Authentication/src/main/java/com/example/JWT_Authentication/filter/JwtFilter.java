package com.example.JWT_Authentication.filter;

import com.example.JWT_Authentication.securityConfig.Security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    Security security;

    @Override
    protected void doFilterInterval(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, Exception {

        filterChain.doFilter(req, res);
    }
}
