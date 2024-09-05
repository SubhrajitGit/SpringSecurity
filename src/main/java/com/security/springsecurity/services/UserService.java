package com.security.springsecurity.services;

import com.security.springsecurity.model.User;
import com.security.springsecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    @Autowired
//    private PasswordEncoder encode;

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

    public ResponseEntity<?> verifyUser(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(user.getUsername()));
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
