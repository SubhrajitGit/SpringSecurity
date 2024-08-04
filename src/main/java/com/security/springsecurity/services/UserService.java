package com.security.springsecurity.services;

import com.security.springsecurity.model.User;
import com.security.springsecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo repository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<String> createUser(User user) {
        User existUser = repository.findByUsername(user.getUsername());
        if (existUser != null) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        else{
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            return ResponseEntity.ok("User created");
        }
    }
}
