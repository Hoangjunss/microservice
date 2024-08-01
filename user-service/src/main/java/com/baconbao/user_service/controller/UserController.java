package com.baconbao.user_service.controller;

import com.baconbao.user_service.AuthService;
import com.baconbao.user_service.security.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @GetMapping("/ourUserDetailsService")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable String username) {
        UserDetails userDetails = ourUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(userDetails);
    }
}
