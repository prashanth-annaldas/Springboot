package com.example.StudentManagementSystem_Backend;

import com.example.StudentManagementSystem_Backend.entity.Users;
import com.example.StudentManagementSystem_Backend.repository.UsersRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostConstruct
    public void init() {

        if(userRepository.count() == 0) {

            Users admin = new Users();

            admin.setName("Prashanth");

            admin.setEmail("admin@gmail.com");

            admin.setPassword(
                    encoder.encode("Prashanth@453")
            );

            admin.setRole(Users.Role.ADMIN);

            admin.setStatus(Users.Status.ACTIVE);

            userRepository.save(admin);

            System.out.println("Admin Created");
        }
    }
}