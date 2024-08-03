package com.baconbao.user_service.controller;

import com.baconbao.user_service.AuthService;
import com.baconbao.user_service.dto.AuthenticationRequest;
import com.baconbao.user_service.dto.AuthenticationResponse;
import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.security.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/auth")
@RestController
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @GetMapping("/ourUserDetailsService")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable String username) {
        UserDetails userDetails= ourUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(userDetails);
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody AuthenticationRequest signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody AuthenticationRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/isValid")
    public ResponseEntity<AuthenticationResponse> isValid(@RequestBody String token){
        return ResponseEntity.ok(authService.isValid(token));
    }
}
