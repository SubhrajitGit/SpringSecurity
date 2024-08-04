package com.security.springsecurity.controller;

import com.security.springsecurity.model.User;
import com.security.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/allUSer")
    public ResponseEntity<List<User>> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
