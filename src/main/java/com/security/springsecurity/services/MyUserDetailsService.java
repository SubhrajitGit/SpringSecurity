package com.security.springsecurity.services;

import com.security.springsecurity.model.User;
import com.security.springsecurity.model.UserPrinciple;
import com.security.springsecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found"+username);
        }
        return new UserPrinciple(user);
    }
}
