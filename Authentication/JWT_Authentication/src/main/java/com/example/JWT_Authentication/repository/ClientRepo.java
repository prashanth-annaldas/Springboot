package com.example.JWT_Authentication.repository;

import com.example.JWT_Authentication.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    Client findByUsername(String username);
}
