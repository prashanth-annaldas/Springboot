package com.example.StudentManagementSystem_Backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12";

    private final Key key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes()
            );

    public String generateToken(String email, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public boolean validateToken(String token){
        try{
            extractAllClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractByEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public String extractRoleByToken(String token){
        return extractAllClaims(token).get("role", String.class);
    }
}
